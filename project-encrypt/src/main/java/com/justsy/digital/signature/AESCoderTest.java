package com.justsy.digital.signature;

import sun.misc.BASE64Encoder;

/**
 * 
 * 文件名 : AESCoderTest.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-24
 */
public class AESCoderTest {
	public static void main(String[] args) throws Exception {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		
		// 生成密钥
		String data = "中华人民共和国";
		String base64key = base64Encoder.encode(AESCoder.initKey());
		System.out.println("密钥 : " + base64key);
		// 加密
		String base64Enc = AESCoder.encrypt(data, base64key) ;
		System.out.println("加密后: " + base64Enc);
		// 解密
		String decStr = AESCoder.decrypt(base64Enc, base64key);
		System.out.println("解密后: " + decStr);
	}
}
