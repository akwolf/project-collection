package com.justsy.digital.signature;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 
 * 文件名 : DESedeCoder.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-24
 */
public abstract class DESedeCoder {
	
	private static final String CLAZZ_NAME = DESedeCoder.class.getName() ;

	private static BASE64Encoder base64Encoder = new BASE64Encoder();
	private static BASE64Decoder base64decoder = new BASE64Decoder();
	private static final byte[] IV = { 127, 124, 70, 27, 105, -94, -86, 40 };
	// 密钥算法
	public static final String KEY_ALGORITHM = "DESede";
	// 加密/解密 / 工作模式 / 填充方式
	public static final String CIPHER_ALGORITHM = "DESede/CBC/PKCS5Padding";

//	public static String getIV() {
//		return base64Encoder.encode(IV);
//	}

	public static Key toKey(byte[] key) throws Exception {
		// 实例化DESede密钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		return securekey;
	}



	public static byte[] encrypt(byte[] data, byte[] key, byte[] iv,
			SecureRandom sr) throws Exception {
		Key k = toKey(key);
		
		System.out.println(CLAZZ_NAME+" encrypt SecretKey Base64 : "+base64Encoder.encode(key));
		System.out.println(CLAZZ_NAME+" encrypt SecretKey Base64 : "+base64Encoder.encode(k.getEncoded()));
		
		IvParameterSpec ips = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k, ips, sr);
		return cipher.doFinal(data);
	}
	
	

	public static String encrypt(String data, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte[] dataBytes = data.getBytes();
		byte[] keyBytes = base64decoder.decodeBuffer(key);
		return base64Encoder.encode(encrypt(dataBytes, keyBytes, IV, sr));
	}
	
	
	public static byte[] decrypt(byte[] data, byte[] key, byte[] iv,
			SecureRandom sr) throws Exception {
		Key k = toKey(key);
		
		System.out.println(CLAZZ_NAME+" decrypt SecretKey Base64 : "+base64Encoder.encode(k.getEncoded()));
		
		IvParameterSpec ips = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 设置为加密模式
		cipher.init(Cipher.DECRYPT_MODE, k, ips, sr);
		return cipher.doFinal(data);
	} 
	
	public static String decrypt(String data, String key) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte[] dataBytes = base64decoder.decodeBuffer(data);
		byte[] keyBytes = base64decoder.decodeBuffer(key);
		return new String(decrypt(dataBytes, keyBytes, IV, sr));
	}

	public String encode3DES(String strEncode) {
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

		String enc = new String(new BASE64Encoder().encode(bt));

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

		// --------------
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, securekey, ips, sr);
		String d = new String(c.doFinal(new BASE64Decoder().decodeBuffer(str)));
		// --------------

		return d;
	}
	
	
	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// DESede【112、168】
		kg.init(112);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获得二进制编码形式
		return secretKey.getEncoded();
	}
}
