package com.marcarndt.morse.chefapi;

import com.marcarndt.morse.chefapi.method.Delete;
import com.marcarndt.morse.chefapi.method.Get;
import com.marcarndt.morse.chefapi.method.Post;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * The type Chef api client.
 */
public class ChefApiClient {

  /**
   * Endpoint.
   */
  private transient final String endpoint;
  /**
   * User ID.
   */
  private transient final String userId;
  /**
   * Path to the chef pem key.
   */
  private transient final String pemPath;

  /**
   * Instantiates a new Chef api client.
   *
   * @param userId user name correspond to the pem KEY
   * @param pemPath path of the auth KEY
   * @param endpoint chef api SERVER address
   */
  public ChefApiClient(final String userId, final String pemPath, final String endpoint) {
    this.userId = userId;
    this.pemPath = pemPath;
    this.endpoint = endpoint;
  }

  /**
   * Get get.
   *
   * @param path in the endpoint. e.g /clients
   * @return the get
   */
  public Get get(final String path) {
    final Get get = new Get(new GetMethod(endpoint + path));
    get.setPemPath(pemPath);
    get.setUserId(userId);
    return get;
  }

  /**
   * Post post.
   *
   * @param path the path
   * @return the post
   */
  public Post post(final String path) {
    final Post post = new Post(new PostMethod(endpoint + path));
    post.setPemPath(pemPath);
    post.setUserId(userId);
    return post;
  }

  /**
   * Delete delete.
   *
   * @param path the path
   * @return the delete
   */
  public Delete delete(final String path) {
    final Delete del = new Delete(new DeleteMethod(endpoint + path));
    del.setPemPath(pemPath);
    del.setUserId(userId);
    return del;
  }

}
