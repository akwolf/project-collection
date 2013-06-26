package com.childe.san.icon;

import java.io.File;

import com.justsy.app.common.png.converter.ConvertHandler;

/**
 * 测试解密Ipa应用Icon图标
 * 
 * 文件名 : Main.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2012-10-16		
 */
public class Main {
	public static void main(String[] args) {
		File f = new File("D:\\Icon.png");
		//		new ConvertHandler(f);
		ConvertHandler ch = new ConvertHandler();
		File file = ch.convertPNGFile(f) ;
		System.out.println(file.getAbsolutePath());
		
	}

}
