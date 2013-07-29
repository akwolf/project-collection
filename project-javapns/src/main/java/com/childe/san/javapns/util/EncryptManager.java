package com.childe.san.javapns.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class EncryptManager
{
  protected static final Logger logger = Logger.getLogger(EncryptManager.class);
  private static EncryptManager instance;

  public static EncryptManager getInstance()
  {
    if (instance == null)
      synchronized (EncryptManager.class)
      {
        instance = new EncryptManager();
      }
    return instance;
  }

  public String decodeBase64String(String paramString)
  {
    String str = new String(Base64.decodeBase64(paramString));
    return str;
  }
}