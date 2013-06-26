package com.justsy.digital.signature;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA签名算法
 * 
 * 文件名 : RSACoder.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-23
 */
public abstract class RSACoder {

	// 密钥算法
	public static final String KEY_ALGORITHM = "RSA";

	// 签名/验证算法
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	// 公钥
	public static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	// 密钥长度
	private static final int KEY_SIZE = 1024;

	/**
	 * 签名
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
		// 转换私钥材料
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey);
		// 实例化密钥工程
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 实例化签名对象
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新数据
		signature.update(data);
		return signature.sign();
	}
	
	public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception{
		// 转换公钥材料
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey) ;
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) ;
		// 生成公钥
		PublicKey pubKey = keyFactory.generatePublic(keySpec) ;
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM) ;
		// 初始化Signature
		signature.initVerify(pubKey) ;
		// 更新
		signature.update(data) ;
		// 验证
		return signature.verify(sign) ;
	}

	public static Map<String, Key> initKey() throws Exception {
		// 初始化密钥对生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		// 初始化密钥对生成器
		keyPairGenerator.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Key> keyMap = new HashMap<String, Key>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	// 取公钥
	public static byte[] getPublicKey(Map<String, Key> keyMap) {
		return getKey(keyMap, PUBLIC_KEY);
	}

	// 取私钥
	public static byte[] getPrivateKey(Map<String, Key> keyMap) {
		return getKey(keyMap, PRIVATE_KEY);
	}

	private static byte[] getKey(Map<String, Key> keyMap, String keyType) {
		Key key = keyMap.get(keyType);
		return key.getEncoded();
	}
	
	/**
	 * 
	 * 私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception{
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key) ;
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) ;
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec) ;
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm()) ;
		cipher.init(Cipher.ENCRYPT_MODE, privateKey) ;
		return cipher.doFinal(data) ;
		
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key) ;
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) ;
		PublicKey publicKey = keyFactory.generatePublic(keySpec) ;
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm()) ;
		cipher.init(Cipher.ENCRYPT_MODE, publicKey) ;
		return cipher.doFinal(data) ;
		
	}
	
	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key) ;
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) ;
		PublicKey publicKey = keyFactory.generatePublic(keySpec) ;
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm()) ;
		cipher.init(Cipher.DECRYPT_MODE, publicKey) ;
		return cipher.doFinal(data) ;
		
	}
	
	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public  static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key) ;
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM) ;
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec) ;
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm()) ;
		cipher.init(Cipher.DECRYPT_MODE, privateKey) ;
		return cipher.doFinal(data) ;
		
	}
}
