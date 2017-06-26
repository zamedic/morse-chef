package com.marcarndt.morse.service;

import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.chefapi.ChefApiClient;
import com.marcarndt.morse.chefapi.method.ApiMethod;
import com.marcarndt.morse.data.ChefDetails;
import com.marcarndt.morse.dto.Node;
import java.io.File;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.mongodb.morphia.query.Query;

/**
 * Created by arndt on 2017/04/10.
 */
@Singleton
public class ChefService {

  static Logger LOG = Logger.getLogger(ChefService.class.getName());

  @Inject
  MongoService mongoService;

  ChefApiClient chefClient;
  ChefDetails chefDetails;

  @PostConstruct
  public void setup() {
    Query<ChefDetails> query = mongoService.getDatastore().createQuery(ChefDetails.class);
    if (query.count() == 0) {
      chefDetails = new ChefDetails();
    } else {
      chefDetails = query.get();
      initializeClient();
    }
  }

  private void initializeClient() {
    chefClient = new ChefApiClient(chefDetails.getUserName(), chefDetails.getKeyPath(),
        chefDetails.getServerUrl());
  }

  public String getSearchURL() {
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search").execute();
    return response.getResponseBodyAsString();
  }

  public List<Node> recipeSearch(String recipe) {
    LOG.info("Fetching Nodes from chef.");
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search/dto?q=recipe:" + recipe)
        .execute();
    JsonReader reader = Json.createReader(new StringReader(response.getResponseBodyAsString()));
    JsonObject rootObject = reader.readObject();
    JsonArray array = rootObject.getJsonArray("rows");
    ArrayList<Node> nodes = new ArrayList();
    for (JsonValue value : array) {
      JsonObject jsonStructure = (JsonObject) value;
      try {
        nodes.add(getNode(jsonStructure));
      } catch (MorseBotException e) {
        LOG.info("Ignoring node due ot error - " + e.getMessage());
      }
    }
    return nodes;

  }

  public Node getNode(String node) throws MorseBotException {
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/nodes/" + node)
        .execute();
    if (response.getReturnCode() != 200) {
      LOG.severe("Could not find node " + node + ". Response Code: " + response.getReturnCode()
          + ". Error: " + response.getResponseBodyAsString());
      throw new MorseBotException(
          "Could not find dto: " + node + " Please check system logs for details ");

    }
    JsonReader reader = Json.createReader(new StringReader(response.getResponseBodyAsString()));
    JsonObject rootObject = reader.readObject();
    return getNode(rootObject);
  }

  private Node getNode(JsonObject rootObject) throws MorseBotException {
    String name = rootObject.getString("name");
    String env = rootObject.getString("chef_environment");
    JsonObject jsonObject = rootObject.getJsonObject("automatic");
    String platform = "";
    String ipAddress = "";
    if (jsonObject.containsKey("platform")) {
      platform = jsonObject.getString("platform");
    }

    jsonObject = rootObject.getJsonObject("automatic");
    if (!jsonObject.containsKey("ipaddress")) {
      //Try and find the IP based on hostname
      try {
        InetAddress address = InetAddress.getByName(name + ".standardbank.co.za");
        ipAddress = address.getHostAddress();
      } catch (UnknownHostException e) {
        throw new MorseBotException("No Ip Address for dto");
      }
    } else {
      ipAddress = jsonObject.getString("ipaddress");
    }

    Node nodeObject = new Node(name, env, platform, ipAddress);
    return nodeObject;
  }

  public void updateKey(String keyPath) throws MorseBotException {
    ChefDetails chefDetails = getChefDetails();

    File file = new File(keyPath);
    if (!file.exists()) {
      throw new MorseBotException("cannot find key file " + keyPath);
    }

    chefDetails.setKeyPath(keyPath);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  private ChefDetails getChefDetails() {
    ChefDetails chefDetails = mongoService.getDatastore().createQuery(ChefDetails.class).get();
    if (chefDetails == null) {
      chefDetails = new ChefDetails();
    }
    return chefDetails;
  }

  public void updateOrg(String org) {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setOrginisation(org);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  public void updateServer(String server) {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setServerUrl(server);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  public void updateUser(String user) {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setUserName(user);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }
}
