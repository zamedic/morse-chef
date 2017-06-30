package com.marcarndt.morse.dto;

/**
 * Created by arndt on 2017/04/13.
 */
public class ChefNode {

  /**
   * ChefNode name
   */
  private String name;
  /**
   * ChefNode environment
   */
  private String environment;
  /**
   * ChefNode platform
   */
  private String platform;
  /**
   * ChefNode IP Address
   */
  private String ipAddress;

  /**
   * ChefNode DTO Object.
   *
   * @param name node name
   * @param environment node environment
   * @param platform node platform
   * @param ipAddress node IP Address
   */
  public ChefNode(final String name, final String environment, final String platform, final String ipAddress) {
    this.name = name;
    this.environment = environment;
    this.platform = platform;
    this.ipAddress = ipAddress;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets environment.
   *
   * @return the environment
   */
  public String getEnvironment() {
    return environment;
  }

  /**
   * Sets environment.
   *
   * @param environment the environment
   */
  public void setEnvironment(final String environment) {
    this.environment = environment;
  }

  /**
   * Gets platform.
   *
   * @return the platform
   */
  public String getPlatform() {
    return platform;
  }

  /**
   * Sets platform.
   *
   * @param platform the platform
   */
  public void setPlatform(final String platform) {
    this.platform = platform;
  }

  /**
   * Gets ip address.
   *
   * @return the ip address
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * Sets ip address.
   *
   * @param ipAddress the ip address
   */
  public void setIpAddress(final String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * @inheritDoc
   */
  @Override
  public String toString() {
    return "name='" + name + '\''
        + ", environment='" + environment + '\''
        + ", platform='" + platform + '\'';

  }
}
