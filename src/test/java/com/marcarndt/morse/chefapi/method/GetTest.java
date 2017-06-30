package com.marcarndt.morse.chefapi.method;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Created by arndt on 2017/06/29.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiMethod.class, Date.class})
public class GetTest {

  @Mock
  HttpClient httpClient;
  @Mock
  Date date;
  @Spy
  GetMethod getMethod = new GetMethod("/test");


  @Test
  public void execute() {
    try {
      setFinalStatic(ApiMethod.class.getDeclaredField("CLIENT"), httpClient);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
    try {
      PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(date);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    when(date.getTime()).thenReturn(1234567890L);
    Get get = new Get(getMethod);
    get.setPemPath(getClass().getResource("/2048b-rsa-example-keypair.pem").getPath());
    get.setUserId("zamedic");
    get.execute();

    verify(getMethod).addRequestHeader("Content-type","application/json");
    verify(getMethod).addRequestHeader("X-Ops-Timestamp","1970-01-15T06:56:07");
    verify(getMethod).addRequestHeader("X-Ops-Userid","zamedic");
    verify(getMethod).addRequestHeader("X-Chef-Version","0.10.4");
    verify(getMethod).addRequestHeader("Accept","application/json");
    verify(getMethod).addRequestHeader("X-Ops-Content-Hash","2jmj7l5rSw0yVb/vlWAYkK/YBwk=");
    verify(getMethod).addRequestHeader("X-Ops-Sign","version=1.0");

    verify(getMethod).addRequestHeader("X-Ops-Authorization-1",
        "kF8U8VEsNKUJ6FGKtVI0BjoRT+dEcCAVGCA5KdVZzfV52fflJb7pvabK1Y23");
    verify(getMethod).addRequestHeader("X-Ops-Authorization-2",
        "YjxuiF/htD41XzowW4zgq28kAB3KnFPMdOh2qdOHI0b+5gUHVgb60t4gPlAy");
    verify(getMethod).addRequestHeader("X-Ops-Authorization-3",
        "F4gTlHRpGJZeN7H0M4tKs185NZP3IzNie8/QL7NNuvG1Cl1juOgkAOqZXyTH");
    verify(getMethod).addRequestHeader("X-Ops-Authorization-4",
        "2Gmi8K+0yr+dHE7TiSqCxFmDgRls+TKktcSR10uCCdEQWvgNjHL8UGEXUAo+");
    verify(getMethod).addRequestHeader("X-Ops-Authorization-5",
        "TQhtaA2Ex43qBV5KZMpKo1Fko7PAzmvCcdf6yB0pyMKq4Y3b6XEnPlxEdEb8");
    verify(getMethod).addRequestHeader("X-Ops-Authorization-6",
        "ZNPXDF2g1KBi50FSdRPfKrxngbLNBrwjDkGyu60NJg==");

  }

  static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);
    // remove final modifier from field
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, newValue);
  }

}