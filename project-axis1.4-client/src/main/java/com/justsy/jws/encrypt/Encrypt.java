package com.justsy.jws.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypt {
	public static String encode3DES(String strEncode) {
		String result = null;
		String strKey = "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6";
		String strIv = "f3xGG2miqig=";
		try {
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] key = base64decoder.decodeBuffer(strKey);
			byte[] iv = base64decoder.decodeBuffer(strIv);
			result = encryptWithIV(key, iv, strEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String encryptWithIV(byte[] key, byte[] iv, String str)
			throws Exception {

		SecureRandom sr = new SecureRandom();

		DESedeKeySpec dks = new DESedeKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

		SecretKey securekey = keyFactory.generateSecret(dks);

		IvParameterSpec ips = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, securekey, ips, sr);

		byte[] bt = cipher.doFinal(str.getBytes("utf-8"));
		
		String enc = new String(new BASE64Encoder().encode(bt)) ;

		return enc;
	}
	
	
	public String decode3DES(String strEncode) {
		String result = null;
		String strKey = "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6";
		String strIv = "f3xGG2miqig=";
		try {
			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] key = base64decoder.decodeBuffer(strKey);
			byte[] iv = base64decoder.decodeBuffer(strIv);
			result = decryptWithIV(key, iv, strEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private static String decryptWithIV(byte[] key, byte[] iv, String str)
			throws Exception {
		SecureRandom sr = new SecureRandom();

		DESedeKeySpec dks = new DESedeKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

		SecretKey securekey = keyFactory.generateSecret(dks);

		IvParameterSpec ips = new IvParameterSpec(iv);

		//--------------
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding") ;
		c.init(Cipher.DECRYPT_MODE, securekey, ips, sr);
		String d = new String(c.doFinal(new BASE64Decoder().decodeBuffer(str))) ;
		//--------------

		return d;
	}
}