package com.justsy.digital.signature;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密
 * 
 * 文件名 : AESCoder.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-24
 */
public abstract class AESCoder {
	private static BASE64Encoder base64Encoder = new BASE64Encoder();
	private static BASE64Decoder base64decoder = new BASE64Decoder();
	// 密钥算法
	public static final String KEY_ALGORITHM = "AES";
	// 加密/解密 / 工作模式 / 填充方式
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	public static Key toKey(byte[] key) throws Exception {
		// 实例化AES密钥材料
		SecretKey securekey = new SecretKeySpec(key, KEY_ALGORITHM);
		return securekey;
	}

	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// AES 密钥长度 128 、192
		// 、256【要对256的支持详见http://ksgimi.iteye.com/blog/1584716】
		kg.init(128);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获得二进制编码形式
		return secretKey.getEncoded();
	}

	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	public static String encrypt(String data, String key) throws Exception {
		return base64Encoder.encode(encrypt(data.getBytes(),
				base64decoder.decodeBuffer(key)));
	}

	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(base64decoder.decodeBuffer(data),
				base64decoder.decodeBuffer(key)));
	}

	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
}
