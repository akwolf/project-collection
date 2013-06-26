package com.justsy.digital.signature;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des3 {  
	
	private static final String CLAZZ_NAME = Des3.class.getName() ;
    // 密钥  
    private final static String secretKey = "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6";  
    // 向量  
    private final static byte[] iv = { 127, 124, 70, 27, 105, -94, -86, 40 };  
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";  
  
    /** 
     * 3DES加密 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encode(String plainText) throws Exception {  
        Key deskey = null;  
//        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        DESedeKeySpec spec = new DESedeKeySpec(Base64.decode(secretKey));  
        byte[] b = Base64.decode(secretKey) ;
        for (int i = 0; i < b.length; i++) {
			System.out.print(b[i]+",");
		}
        System.out.println();
        
        
        
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips,new SecureRandom());  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
        return Base64.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decode(String encryptText) throws Exception {  
        Key deskey = null;  
//        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        DESedeKeySpec spec = new DESedeKeySpec(Base64.decode(secretKey));  
        
        System.out.println(CLAZZ_NAME+" decode SecretKey Base64 : "+Base64.encode(secretKey.getBytes()));
        
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        
        System.out.println(CLAZZ_NAME+" decode SecretKey Base64 : "+Base64.encode(deskey.getEncoded()));
        
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips,new SecureRandom());  
  
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));  
  
        return new String(decryptData, encoding);  
    }  
}  