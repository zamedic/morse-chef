package com.marcarndt.morse.chefapi;

import com.marcarndt.morse.chefapi.method.ApiMethod;
import junit.framework.TestCase;
import org.junit.Test;

public class chefClientTest extends TestCase {

  private static String CHEF_NODE_STR = "{  \"name\": \"latte\",  \"chef_type\": \"node\",  \"json_class\": \"Chef::Node\",  \"attributes\": { \"hardware_type\": \"laptop\" },  \"overrides\": {  },  \"defaults\": {  },  \"run_list\": [ \"recipe[install_pg]\",\"recipe[install_license]\",\"recipe[install_pm]\" ] }";

  @Test
  public void testGet() {
    ChefApiClient cac = new ChefApiClient("wang", "C:/Users/ESVWYZV/wang.pem",
        "http://macloud.dnsdynamic.com:4000");

    ApiMethod am = cac.get("/nodes/node1");
    int code = am.execute().getReturnCode();
    String xx = am.getResponseBodyAsString();
    System.out.println(code + "  \n" + xx);
    cac.delete("/nodes" + "/" + "latte").execute();
  }
}
