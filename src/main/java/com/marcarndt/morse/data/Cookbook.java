package com.marcarndt.morse.data;

import com.marcarndt.swarm.data.Application;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/05/04.
 */
@Entity
public class Cookbook {

  /**
   * Object ID.
   */
  @Id
  private ObjectId objectId;
  /**
   * Cookbook Description.
   */
  private String description;
  /**
   * Cookbook Name.
   */
  private String cookbookName;

  /**
   * Application associated to the cookbookName.
   */
  @Reference
  private Application application;


  /**
   * Cookbook Entity.
   *
   * @param description Cookbook description
   * @param cookbookName cookbookName name
   * @param application Application object
   */
  public Cookbook(final String description, final String cookbookName,
      final Application application) {
    this.description = description;
    this.cookbookName = cookbookName;
    this.application = application;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Gets cookbookName.
   *
   * @return the cookbookName
   */
  public String getCookbookName() {
    return cookbookName;
  }

  /**
   * Sets cookbookName.
   *
   * @param cookbookName the cookbookName
   */
  public void setCookbookName(final String cookbookName) {
    this.cookbookName = cookbookName;
  }

  /**
   * Gets application.
   *
   * @return the application
   */
  public Application getApplication() {
    return application;
  }

  /**
   * Sets application.
   *
   * @param application the application
   */
  public void setApplication(final Application application) {
    this.application = application;
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
