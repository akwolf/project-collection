package com.justsy.test;

import java.net.URLDecoder;

/**
 * 
 * 
 * 文件名 : UrlEncodeTest.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2013-1-26		
 */
public class UrlEncodeTest {

	public static void main(String[] args) {
		System.out.println(new URLDecoder().decode("UserName=hy&Password=Demo05&password_text=%E8%AF%B7%E8%BE%93%E5%85%A5%E5%AF%86%E7%A0%81&submit=%E7%99%BB%E5%BD%95&ServiceProviderID="));
	}

}
