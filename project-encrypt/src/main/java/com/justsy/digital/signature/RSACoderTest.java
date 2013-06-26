package com.justsy.digital.signature;

import java.security.Key;
import java.util.Map;

import sun.misc.BASE64Encoder;

/**
 * 
 * 
 * 文件名 : RSACoderTest.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-23
 */
public class RSACoderTest {
	public static void main(String[] args) throws Exception {
		BASE64Encoder base64Encoder = new BASE64Encoder();

		String inputStr = "RSA加密";
		byte[] data = inputStr.getBytes();
		Map<String, Key> keyMap = RSACoder.initKey();
		byte[] pubKey = RSACoder.getPublicKey(keyMap);
		byte[] priKey = RSACoder.getPrivateKey(keyMap);
		System.out.println("公钥 : " + base64Encoder.encode(pubKey));
		System.out.println("--------------");
		System.out.println("私钥 : " + base64Encoder.encode(priKey));
		System.out.println("--------------");
		System.out.println("私钥加密--公钥解密");
		// 私钥加密
		byte[] encryptBytes = RSACoder.encryptByPrivateKey(data, priKey);
		System.out.println(base64Encoder.encode(encryptBytes));
		System.out.println("--------------");
		// 公钥解密
		byte[] decryptBytes = RSACoder.decryptByPublicKey(encryptBytes, pubKey);
		System.out.println(new String(decryptBytes));
		System.out.println("--------------");
		
		
		System.out.println("公钥加密--私钥解密");
		data = "zhangh".getBytes() ;
		// 公钥加密
		encryptBytes = RSACoder.encryptByPublicKey(data, pubKey);
		System.out.println(base64Encoder.encode(encryptBytes));
		System.out.println("--------------");
		// 私钥解密
		decryptBytes = RSACoder.decryptByPrivateKey(encryptBytes, priKey);
		System.out.println(new String(decryptBytes));
		System.out.println("--------------");

		System.out.println("签名--验证");
		data = "sign".getBytes() ;
		byte[] da = "false".getBytes() ;
		byte[] sign = RSACoder.sign(data, priKey) ;
		System.out.println("sign : "+base64Encoder.encode(sign));
		boolean verify = RSACoder.verify(data, pubKey, sign) ;
		System.out.println("verify : "+verify);
		System.out.println("verify : "+RSACoder.verify(da, pubKey, sign));
	}
}
