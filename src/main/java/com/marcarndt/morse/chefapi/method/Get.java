package com.marcarndt.morse.chefapi.method;

import org.apache.commons.httpclient.HttpMethod;

/**
 * The type Get.
 */
public class Get extends ApiMethod { //NOPMD

  /**
   * Instantiates a new Get.
   *
   * @param method the method
   */
  public Get(final HttpMethod method) {
    super("GET");
    this.method = method;
  }

}
