package com.marcarndt.morse.service;

import com.marcarndt.morse.data.Cookbook;
import com.marcarndt.swarm.data.Application;
import com.marcarndt.swarm.service.ApplicationService;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class ChefApplicationService {

  private Logger logger = Logger.getLogger(ChefApplicationService.class.getName());

  /**
   * The Mongo service.
   */
  @Inject
  MongoService mongoService;

  /**
   * The Application service.
   */
  @Inject
  ApplicationService applicationService;

  /**
   * Add recipe to application.
   *
   * @param application the application
   * @param description the description
   * @param recipe the recipe
   */
  public void addRecipeToApplication(String application, String description, String recipe) {
    Application applicationObject = applicationService.getApplication(application);

    Cookbook cookbook = new Cookbook(description, recipe, applicationObject);
    mongoService.getDatastore().save(cookbook);
  }


  /**
   * Gets cookbook.
   *
   * @param description the description
   * @return the cookbook
   */
  public Cookbook getCookbook(String description) {
    return mongoService.getDatastore().createQuery(Cookbook.class).field("description")
        .equal(description).get();
  }


}
