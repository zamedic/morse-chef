package com.marcarndt.morse.chefapi.method;

import com.marcarndt.morse.chefapi.Utils;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The type Api method.
 */
public class ApiMethod {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(ApiMethod.class.getName());

  /**
   * The Method.
   */
  protected transient HttpMethod method;
  /**
   * The Req body.
   */
  protected String reqBody = "";
  /**
   * The UpdateChefUser id.
   */
  protected String userId = "";
  /**
   * The Pem path.
   */
  protected String pemPath = "";
  /**
   * HTTP Client.
   */
  private static final  HttpClient CLIENT = new HttpClient();
  /**
   * HTTP Method.
   */
  private String methodName = "GET";//NOPMD

  /**
   * Return Code.
   */
  private transient int returnCode;

  /**
   * Instantiates a new Api method.
   *
   * @param methodName the method name
   */
  public ApiMethod(final String methodName) {
    this.methodName = methodName;
  }

  /**
   * Execute api method.
   *
   * @return the api method
   */
  public ApiMethod execute() {
    final String hashedPath = Utils.sha1AndBase64(method.getPath());
    final String hashedBody = Utils.sha1AndBase64(reqBody);

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String timeStamp = sdf.format(new Date());
    timeStamp = timeStamp.replace(" ", "T");

    final StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append("Method:").append(methodName)
        .append("\nHashed Path:").append(hashedPath)
        .append("\nX-Ops-Content-Hash:").append(hashedBody)
        .append("\nX-Ops-Timestamp:").append(timeStamp)
        .append("\nX-Ops-UserId:").append(userId);

    final String authString = Utils.signWithRsa(stringBuilder.toString(), pemPath);
    final String[] authHeaders = Utils.splitAs60(authString);

    method.addRequestHeader("Content-type", "application/json");
    method.addRequestHeader("X-Ops-Timestamp", timeStamp);
    method.addRequestHeader("X-Ops-Userid", userId);
    method.addRequestHeader("X-Chef-Version", "0.10.4");
    method.addRequestHeader("Accept", "application/json");
    method.addRequestHeader("X-Ops-Content-Hash", hashedBody);
    method.addRequestHeader("X-Ops-Sign", "version=1.0");

    for (int i = 0; i < authHeaders.length; i++) {
      method.addRequestHeader("X-Ops-Authorization-" + (i + 1), authHeaders[i]);
    }

    try {
      returnCode = CLIENT.executeMethod(method);
    } catch (HttpException e) {
      LOG.log(Level.SEVERE, "HTTP Error", e);
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "IO Error", e);
    }

    return this;
  }

  /**
   * Sets headers.
   *
   * @param headers the headers
   */
  public void setHeaders(final Header... headers) {
    for (final Header header : headers) {
      this.method.addRequestHeader(header);
    }
  }

  /**
   * Gets response body as string.
   *
   * @return the response body as string
   */
  public String getResponseBodyAsString() {
    String reqBody = null;
    try {
      reqBody = method.getResponseBodyAsString();
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "IO Error", e);
    } finally {
      method.releaseConnection();
    }
    return reqBody;
  }

  /**
   * Gets return code.
   *
   * @return the return code
   */
  public int getReturnCode() {
    return returnCode;
  }

  /**
   * Gets req body.
   *
   * @return the req body
   */
  public String getReqBody() {
    return reqBody;
  }

  /**
   * Sets req body.
   *
   * @param body the body
   */
  public void setReqBody(final String body) {
    this.reqBody = body;
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(final String userId) {
    this.userId = userId;
  }

  /**
   * Gets pem path.
   *
   * @return the pem path
   */
  public String getPemPath() {
    return pemPath;
  }

  /**
   * Sets pem path.
   *
   * @param pemPath the pem path
   */
  public void setPemPath(final String pemPath) {
    this.pemPath = pemPath;
  }
}
