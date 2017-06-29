package com.marcarndt.morse.service;

import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.chefapi.ChefApiClient;
import com.marcarndt.morse.chefapi.method.ApiMethod;
import com.marcarndt.morse.data.ChefDetails;
import com.marcarndt.morse.dto.Node;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.mongodb.morphia.query.Query;

/**
 * Created by arndt on 2017/04/10.
 */
@Singleton
public class ChefService {

  /**
   * The Log.
   */
  static Logger LOG = Logger.getLogger(ChefService.class.getName());

  /**
   * The Mongo service.
   */
  @Inject
  MongoService mongoService;

  /**
   * The Chef client.
   */
  ChefApiClient chefClient;
  /**
   * The Chef details.
   */
  ChefDetails chefDetails;

  /**
   * Sets .
   */
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

  /**
   * Gets search url.
   *
   * @return the search url
   */
  public String getSearchURL() {
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search").execute();
    return response.getResponseBodyAsString();
  }

  /**
   * Recipe search list.
   *
   * @param recipe the recipe
   * @return the list
   */
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

  /**
   * Gets node.
   *
   * @param node the node
   * @return the node
   * @throws MorseBotException the morse bot exception
   */
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

  /**
   * Update key.
   *
   * @param keyPath the key path
   * @throws MorseBotException the morse bot exception
   */
  public void updateKey(String keyPath) throws MorseBotException {
    ChefDetails chefDetails = getChefDetails();

    File file = new File(keyPath);
    if (!file.exists()) {
      throw new MorseBotException("cannot find KEY file " + keyPath);
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

  /**
   * Update org.
   *
   * @param org the org
   */
  public void updateOrg(String org) {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setOrginisation(org);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  /**
   * Update server.
   *
   * @param server the server
   * @throws MorseBotException the morse bot exception
   */
  public void updateServer(String server) throws MorseBotException {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setServerUrl(server);
    mongoService.getDatastore().save(chefDetails);
    setup();
    try {
      addCert(server);
    } catch (KeyStoreException e) {
      LOG.log(Level.SEVERE, "Key Store exception", e);
      throw new MorseBotException(
          "we were unable to download and add the certificate to the default keystore. " + e
              .getMessage());
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "IO exception", e);
      throw new MorseBotException(
          "we were unable to download and add the certificate to the default keystore. " + e
              .getMessage());
    } catch (CertificateException e) {
      LOG.log(Level.SEVERE, "Certificate exception", e);
      throw new MorseBotException(
          "we were unable to download and add the certificate to the default keystore. " + e
              .getMessage());
    } catch (NoSuchAlgorithmException e) {
      LOG.log(Level.SEVERE, "No such algorithm exception exception", e);
      throw new MorseBotException(
          "we were unable to download and add the certificate to the default keystore. " + e
              .getMessage());
    } catch (KeyManagementException e) {
      LOG.log(Level.SEVERE, "Key management exception", e);
      throw new MorseBotException(
          "we were unable to download and add the certificate to the default keystore. " + e
              .getMessage());
    }

  }

  /**
   * Update user.
   *
   * @param user the user
   */
  public void updateUser(String user) {
    ChefDetails chefDetails = getChefDetails();
    chefDetails.setUserName(user);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  private void addCert(String url)
      throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {

    final char sep = File.separatorChar;
    File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");
    File file = new File(dir, "cacerts");
    InputStream localCertIn = new FileInputStream(file);

    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(localCertIn, "changeit".toCharArray());
    
    localCertIn.close();
    Certificate[] certs = certInformation(url);

    keystore.setCertificateEntry("chef"+ Math.random(), certs[0]);

    OutputStream out = new FileOutputStream(file);
    keystore.store(out, "changeit".toCharArray());
    out.close();
  }

  private Certificate[] certInformation(String aurl)
      throws IOException, NoSuchAlgorithmException, KeyManagementException {
    URL destinationUrl = new URL(aurl);
    HttpsURLConnection conn = (HttpsURLConnection) destinationUrl.openConnection();
    SSLContext sc = SSLContext.getInstance("TLS");
    sc.init(null, new TrustManager[]{new X509TrustManager() {

      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
          throws CertificateException {

      }

      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
          throws CertificateException {

      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    }}, new java.security.SecureRandom());
    conn.setSSLSocketFactory(sc.getSocketFactory());
    conn.connect();
    return conn.getServerCertificates();
  }


  /**
   * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has
   * been created to aid testing on a local box, not for use on production.
   */
  private static void disableSSLCertificateChecking() {
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      @Override
      public void checkClientTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {
        // Not implemented
      }

      @Override
      public void checkServerTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {
        // Not implemented
      }
    }};

    try {
      SSLContext sc = SSLContext.getInstance("TLS");

      sc.init(null, trustAllCerts, new java.security.SecureRandom());

      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
