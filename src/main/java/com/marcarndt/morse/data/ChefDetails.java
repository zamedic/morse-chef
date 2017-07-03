package com.marcarndt.morse.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/16.
 */
@Entity()
public class ChefDetails {

  /**
   * Object ID.
   */
  @Id
  private ObjectId objectId;
  /**
   * Server URL.
   */
  private String serverUrl;
  /**
   * User name.
   */
  private String userName;
  /**
   * Key path.
   */
  private String keyPath;
  /**
   * chef orignisation.
   */
  private String orginisation;

  /**
   * Gets server url.
   *
   * @return the server url
   */
  public String getServerUrl() {
    return serverUrl;
  }

  /**
   * Sets server url.
   *
   * @param serverUrl the server url
   */
  public void setServerUrl(final String serverUrl) {
    this.serverUrl = serverUrl;
  }

  /**
   * Gets user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets user name.
   *
   * @param userName the user name
   */
  public void setUserName(final String userName) {
    this.userName = userName;
  }

  /**
   * Gets key path.
   *
   * @return the key path
   */
  public String getKeyPath() {
    return keyPath;
  }

  /**
   * Sets key path.
   *
   * @param keyPath the key path
   */
  public void setKeyPath(final String keyPath) {
    this.keyPath = keyPath;
  }

  /**
   * Gets orginisation.
   *
   * @return the orginisation
   */
  public String getOrginisation() {
    return orginisation;
  }

  /**
   * Sets orginisation.
   *
   * @param orginisation the orginisation
   */
  public void setOrginisation(final String orginisation) {
    this.orginisation = orginisation;
  }

  /**
   * Gets object id.
   *
   * @return the object id
   */
  public ObjectId getObjectId() {
    return objectId;
  }

  /**
   * Sets object id.
   *
   * @param objectId the object id
   */
  public void setObjectId(final ObjectId objectId) {
    this.objectId = objectId;
  }
}
