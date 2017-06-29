package com.marcarndt.morse.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * The type Post.
 */
public class Post extends ApiMethod {//NOPMD

  /**
   * Instantiates a new Post.
   *
   * @param method the method
   */
  public Post(final HttpMethod method) {
    super("POST");
    this.method = method;
  }

  /**
   * Body api method.
   *
   * @param body the body
   * @return the api method
   */
  public ApiMethod body(final String body) {
    this.reqBody = body;
    final PostMethod post = (PostMethod) method;
    post.setRequestBody(body);
    return this;
  }

}
