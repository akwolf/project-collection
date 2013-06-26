package com.childe.san.plist;

import java.io.File;

import com.dd.plist.PropertyListParser;

/**
 * 解密Info.plist文件
 * 
 * 文件名 : Main.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2012-10-16		
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// 密件
		File f = new File("D:\\Info.plist") ;
		// 被解密件
		File nf = new File("D:\\NInfo.plist") ;
		
		PropertyListParser.convertToXml(f, nf);
	}
	
	
	

}
