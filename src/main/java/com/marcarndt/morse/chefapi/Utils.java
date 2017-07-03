package com.marcarndt.morse.chefapi;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Base64;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Utils.
 */
public final class Utils {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  private Utils() {
  }

  /**
   * Sha 1 and base 64 string.
   *
   * @param inStr the in str
   * @return the string
   */
  public static String sha1AndBase64(final String inStr) {
    MessageDigest messageDigest = null;
    byte[] outbty = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-1");
      final byte[] digest = messageDigest.digest(inStr.getBytes(Charset.defaultCharset()));
      outbty = Base64.encode(digest);
    } catch (NoSuchAlgorithmException nsae) {
      LOG.log(Level.SEVERE, "ERROR on chef util", nsae);
    }
    return new String(outbty, Charset.defaultCharset());
  }

  /**
   * Sign with rsa string.
   *
   * @param inStr the in str
   * @param pemPath the pem path
   * @return the string
   */
  public static String signWithRsa(final String inStr, final String pemPath) {
    byte[] outStr = null;
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(pemPath),
          StandardCharsets.UTF_8));
    } catch (FileNotFoundException e) {
      LOG.log(Level.SEVERE, "Error on chef util RSA", e);
    }
    Security.addProvider(new BouncyCastleProvider());
    try {
      final PEMReader pemReader = new PEMReader(bufferedReader);
      final KeyPair keyPair = (KeyPair) pemReader.readObject();
      pemReader.close();
      final PrivateKey privateKey = keyPair.getPrivate();
      final Signature instance = Signature.getInstance("RSA");
      instance.initSign(privateKey);
      instance.update(inStr.getBytes(StandardCharsets.UTF_8));

      final byte[] signature = instance.sign();
      outStr = Base64.encode(signature);
    } catch (InvalidKeyException e) {
      LOG.log(Level.SEVERE, "Invalid AskForChefKey", e);
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "IO Error", e);
    } catch (SignatureException e) {
      LOG.log(Level.SEVERE, "Signature Exception", e);
    } catch (NoSuchAlgorithmException e) {
      LOG.log(Level.SEVERE, "Could not find algorithm", e);
    }
    return new String(outStr, StandardCharsets.UTF_8);
  }

  /**
   * Split as 60 string [ ].
   *
   * @param inStr the in str
   * @return the string [ ]
   */
  public static String[] splitAs60(final String inStr) {
    final int count = inStr.length() / 60;
    String[] out = new String[count + 1];

    for (int i = 0; i < count; i++) {
      final String tmp = inStr.substring(i * 60, i * 60 + 60);
      out[i] = tmp;
    }
    if (inStr.length() > count * 60) {
      final String tmp = inStr.substring(count * 60, inStr.length());
      out[count] = tmp;
    }
    return out;
  }

}
