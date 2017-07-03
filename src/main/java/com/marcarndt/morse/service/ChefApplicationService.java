package com.marcarndt.morse.service;

import com.marcarndt.morse.data.Cookbook;
import com.marcarndt.swarm.data.Application;
import com.marcarndt.swarm.service.ApplicationService;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class ChefApplicationService {

  /**
   * The Mongo service.
   */
  @Inject
  private transient MongoService mongoService;
  /**
   * The Application service.
   */
  @Inject
  private transient ApplicationService appService;

  /**
   * Add recipe to application.
   *
   * @param application the application
   * @param description the description
   * @param recipe the recipe
   */
  public void addRecipeToApplication(final String application, final String description,
      final String recipe) {
    final Application applicationObject = appService.getApplication(application);

    final Cookbook cookbook = new Cookbook(description, recipe, applicationObject);
    mongoService.getDatastore().save(cookbook);
  }


  /**
   * Gets cookbook.
   *
   * @param description the description
   * @return the cookbook
   */
  public Cookbook getCookbook(final String description) {
    return mongoService.getDatastore().createQuery(Cookbook.class).field("description")
        .equal(description).get();
  }


}
