package com.justsy.cryto;

import java.io.IOException;

/**
 * 
 * 
 * 文件名 : Base64Test.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2013-1-24		
 */
public class Base64Test {
	
	public static void main(String[] args) throws IOException {
//		BASE64Decoder base64Decoder = new BASE64Decoder() ;
//		byte[] bs = base64Decoder.decodeBuffer("f3xGG2miqig=") ;
//		for (byte b : bs) {
//			System.out.print(b+",");
//		}
		String s = "wangyifan012345|38674776|2013-01-24 09:01:35" ;
//		String str = s.split("||")[0] ;
		String strs[] = s.split("\\|") ;
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]);
		}
	}

}
