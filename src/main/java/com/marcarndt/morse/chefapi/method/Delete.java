package com.marcarndt.morse.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;

/**
 * The type Delete.
 */
public class Delete extends ApiMethod {

  /**
   * Instantiates a new Delete.
   *
   * @param method the method
   */
  public Delete(final HttpMethod method) {
    super("DELETE");
    this.method = method;
  }

}
