package com.justsy.test;

import com.justsy.digital.signature.Base64;

/**
 * 
 * 
 * 文件名 : Hash.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2013-2-25		
 */
public class Hash {
	public static void main(String[] args) {
		System.out.println(Base64.encode("00-50-56-C0-00-08".getBytes()));
		System.out.println(Base64.encode("00:50:56:C0:00:08".getBytes()));
		System.out.println(Base64.encode("t-".getBytes()));
	}
}
