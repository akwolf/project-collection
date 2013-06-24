package com.justsy.autoclean.example;

import java.io.File;
import java.io.IOException;

/**
 * 
 * 
 * 文件名 : DosCMD.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-4-16
 */
public class DosCMD {

	public static void main(String[] args) throws IOException,
			InterruptedException {

//		Process process = Runtime.getRuntime().exec(
//				new String[] { "cmd.exe", "/c", "C:\\logs\\echo.bat" });
//		process.waitFor();
		File f = new File("C:\\logs\\echo.bat" ) ;
		System.out.println(f.getPath());
		
	}

}
