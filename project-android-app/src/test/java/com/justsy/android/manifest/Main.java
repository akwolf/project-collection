package com.justsy.android.manifest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * 文件名 : Main.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2012-10-30		
 */
public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("D:\\AndroidManifest.xml");

		File f = new File("D:\\AndroidManifest-New.xml");

		InputStream in = new FileInputStream(file);

		in = AXMLPrinter.startXMLPrint(in);

		new Main().writeFile(f, in);

		in.close();
		System.out.println("done!!");
		
//		String name = "zhang.txt" ;
//		System.out.println(name.substring(name.lastIndexOf(".")+1));
		
		
		
	}
	
	
	protected void writeFile(File file, InputStream in) throws IOException {
		int tmp = 0;
		FileOutputStream fos = new FileOutputStream(file);

		byte[] buffer = new byte[512];

		while ((tmp = in.read(buffer)) != -1) {
			fos.write(buffer, 0, tmp);
		}
		fos.close();
	}

}
