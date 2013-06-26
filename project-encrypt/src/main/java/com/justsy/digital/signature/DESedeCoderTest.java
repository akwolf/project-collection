package com.justsy.digital.signature;

import sun.misc.BASE64Encoder;

/**
 * 
 * 
 * 文件名 : DESedeCoderTest.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-1-24
 */
public class DESedeCoderTest {

	
	
	public static void main(String[] args) throws Exception {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		// 生成密钥
//		String data = "wangyifan012345|38674776|2013-01-24 08:35:24";
//		System.out.println("加密前: " + data);
//		byte[] b = DESedeCoder.initKey() ;
//		String base64key = base64Encoder.encode(b);
//		System.out.println("密钥 : " + "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6");
//		System.out.println("密钥 : " + base64key);
//		System.out.println("密钥 : " + Base64.encode(b));
		// 加密
		String base64Enc = DESedeCoder.encrypt("wangwei", "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6") ;
		System.out.println("加密后: " + base64Enc);
		// 解密
//		String decStr = DESedeCoder.decrypt("CnMYheadDkashuKGZKzYYdgJoB1ihv5WMxBkiIVhcctKR0GYu4zg7zRMDVkFT6kW", "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6");
		System.out.println("解密后: " + DESedeCoder.decrypt(base64Enc, "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6"));
		System.out.println("-----------------");
//		String decStr = DESedeCoder.decrypt("H++SF9t66h4=", "PJ8qmYKz70dAOKUL2O6dhK8qhNKWF0d6");
		String d = Des3.encode("wangwei") ;
		System.out.println(d);
		String decStr = Des3.decode("CnMYheadDkashuKGZKzYYdgJoB1ihv5WQ9c7KcWnthDa2Yqc+B8HFu2DjB+lg7WX") ;
		System.out.println("解密后: " + decStr);
	}
}
