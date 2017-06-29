package com.marcarndt.morse.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PutMethod;

/**
 * The type Put.
 */
public class Put extends ApiMethod {//NOPMD

  /**
   * Instantiates a new Put.
   *
   * @param method the method
   */
  public Put(final HttpMethod method) {
    super("PUT");
    this.method = method;
  }

  /**
   * Body api method.
   *
   * @param body the body
   * @return the api method
   */
  public ApiMethod body(final String body) {
    final PutMethod put = (PutMethod) method;
    put.setRequestBody(body);
    return this;
  }

}
