package com.childe.san.icon;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.childe.san.zip.ZipEntryFinder;
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
	public static void main(String[] args) throws ZipException, IOException {
		File f = new File("D:\\Icon.png");
		File ipa = new File("D:\\Info.ipa") ;
		ZipFile zipFile = new ZipFile(ipa) ;
		ZipEntryFinder entryFinder = new ZipEntryFinder() ;
		ZipEntry ze = entryFinder.findZipEntry(zipFile, "Icon.png") ;
		FileUtils.writeByteArrayToFile(f, IOUtils.toByteArray(zipFile.getInputStream(ze))) ;
		
		ConvertHandler ch = new ConvertHandler();
		File file = ch.convertPNGFile(f) ;
		zipFile.close() ;
		System.out.println(file.getAbsolutePath());
		
	}

}
