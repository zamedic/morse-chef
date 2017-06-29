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
  private static final Logger LOG = Logger.getLogger(ChefService.class.getName());
  /**
   * Error Message.
   */
  public static final String ERROR = "we were unable to download and add the certificate to the default keystore. ";

  /**
   * The Mongo service.
   */
  @Inject
  private transient MongoService mongoService;

  /**
   * The Chef client.
   */
  private transient ChefApiClient chefClient;
  /**
   * The Chef details.
   */
  private transient ChefDetails chefDetails;

  /**
   * Sets .
   */
  @PostConstruct
  public void setup() {
    final Query<ChefDetails> query = mongoService.getDatastore().createQuery(ChefDetails.class);
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
    final ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search").execute();
    return response.getResponseBodyAsString();
  }

  /**
   * Recipe search list.
   *
   * @param recipe the recipe
   * @return the list
   */
  public List<Node> recipeSearch(final String recipe) {
    LOG.info("Fetching Nodes from chef.");
    final ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search/dto?q=recipe:" + recipe)
        .execute();
    final JsonReader reader = Json
        .createReader(new StringReader(response.getResponseBodyAsString()));
    final JsonObject rootObject = reader.readObject();
    final JsonArray array = rootObject.getJsonArray("rows");
    final ArrayList<Node> nodes = new ArrayList();
    for (final JsonValue value : array) {
      final JsonObject jsonStructure = (JsonObject) value;
      try {
        nodes.add(getNode(jsonStructure));
      } catch (MorseBotException e) {
        if (LOG.isLoggable(Level.INFO)) {
          LOG.info("Ignoring node due ot ERROR - " + e.getMessage());//NOPMD
        }
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
  public Node getNode(final String node) throws MorseBotException {
    final ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/nodes/" + node)
        .execute();
    if (response.getReturnCode() != 200) {
      if (LOG.isLoggable(Level.SEVERE)) {
        LOG.severe("Could not find node " + node + ". Response Code: " + response.getReturnCode()
            + ". Error: " + response.getResponseBodyAsString());
      }
      throw new MorseBotException(
          "Could not find dto: " + node + " Please check system logs for details ");

    }
    final JsonReader reader = Json
        .createReader(new StringReader(response.getResponseBodyAsString()));
    final JsonObject rootObject = reader.readObject();
    return getNode(rootObject);
  }

  private Node getNode(final JsonObject rootObject) throws MorseBotException {
    final String name = rootObject.getString("name");
    JsonObject jsonObject = rootObject.getJsonObject("automatic");
    String platform = "";
    String ipAddress = "";
    if (jsonObject.containsKey("platform")) {
      platform = jsonObject.getString("platform");
    }

    jsonObject = rootObject.getJsonObject("automatic");
    if (jsonObject.containsKey("ipaddress")) {
      ipAddress = jsonObject.getString("ipaddress");
    } else {
      //Try and find the IP based on hostname
      try {
        final InetAddress address = InetAddress.getByName(name + ".standardbank.co.za");
        ipAddress = address.getHostAddress();
      } catch (UnknownHostException e) {
        LOG.log(Level.SEVERE, "Unable to find host", e);
        throw new MorseBotException("No Ip Address for dto ", e);
      }
    }

    return new Node(name, rootObject.getString("chef_environment"), platform, ipAddress);
  }

  /**
   * Update key.
   *
   * @param keyPath the key path
   * @throws MorseBotException the morse bot exception
   */
  public void updateKey(final String keyPath) throws MorseBotException {

    final File file = new File(keyPath);
    if (!file.exists()) {
      throw new MorseBotException("cannot find KEY file " + keyPath);
    }
    final ChefDetails chefDetails = getChefDetails();
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
  public void updateOrg(final String org) {
    final ChefDetails chefDetails = getChefDetails();
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
  public void updateServer(final String server) throws MorseBotException {
    final ChefDetails chefDetails = getChefDetails();
    chefDetails.setServerUrl(server);
    mongoService.getDatastore().save(chefDetails);
    setup();
    try {
      addCert(server);
    } catch (KeyStoreException e) {
      LOG.log(Level.SEVERE, "Key Store exception", e);
      throw new MorseBotException(
          ERROR + e.getMessage(), e);
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "IO exception", e);
      throw new MorseBotException(
          ERROR + e.getMessage(), e);
    } catch (CertificateException e) {
      LOG.log(Level.SEVERE, "Certificate exception", e);
      throw new MorseBotException(
          ERROR + e.getMessage(), e);
    } catch (NoSuchAlgorithmException e) {
      LOG.log(Level.SEVERE, "No such algorithm exception exception", e);
      throw new MorseBotException(
          ERROR + e.getMessage(), e);
    } catch (KeyManagementException e) {
      LOG.log(Level.SEVERE, "Key management exception", e);
      throw new MorseBotException(
          ERROR + e.getMessage(), e);
    }

  }

  /**
   * Update user.
   *
   * @param user the user
   */
  public void updateUser(final String user) {
    final ChefDetails chefDetails = getChefDetails();
    chefDetails.setUserName(user);
    mongoService.getDatastore().save(chefDetails);
    setup();
  }

  private void addCert(final String url)
      throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {

    final char sep = File.separatorChar;
    final File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");
    final File file = new File(dir, "cacerts");
    final InputStream localCertIn = new FileInputStream(file);

    final KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(localCertIn, "changeit".toCharArray());

    localCertIn.close();
    final Certificate[] certs = certInformation(url);

    keystore.setCertificateEntry("chef" + Math.random(), certs[0]);

    final OutputStream out = new FileOutputStream(file);
    keystore.store(out, "changeit".toCharArray());
    out.close();
  }

  private Certificate[] certInformation(final String aurl)
      throws IOException, NoSuchAlgorithmException, KeyManagementException {
    final URL destinationUrl = new URL(aurl);
    final HttpsURLConnection conn = (HttpsURLConnection) destinationUrl.openConnection();
    final SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, new TrustManager[]{new X509TrustManager() {

      /**
       * Does nothing - this is intentional since we want to accept all certificates.
       *
       * @param x509Certificates certificate
       * @param manager String
       * @throws CertificateException Standard Exception
       */
      @Override
      public void checkClientTrusted(final X509Certificate[] x509Certificates, final String manager)
          throws CertificateException {
        //Does nothing to accept all chef certs.

      }

      /**
       * Does nothing - this is intentional since we want to accept all certificates.
       *
       * @param x509Certificates certificate
       * @param manager String
       * @throws CertificateException Exception
       */
      @Override
      public void checkServerTrusted(final X509Certificate[] x509Certificates, final String manager)
          throws CertificateException {
        //Does nothing to accept all chef certs
      }

      /**
       * Accept all certificates.
       * @return null
       */
      @Override
      public X509Certificate[] getAcceptedIssuers() {//NOPMD
        return null;
      }
    }}, new java.security.SecureRandom());
    conn.setSSLSocketFactory(sslContext.getSocketFactory());
    conn.connect();
    return conn.getServerCertificates();
  }
}
