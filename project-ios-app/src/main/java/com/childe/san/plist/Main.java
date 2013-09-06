package com.childe.san.plist;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.childe.san.zip.ZipEntryFinder;
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
		File f = new File("D:\\Info.plist");
		// 被解密件
		File nf = new File("D:\\NInfo.plist");
		File ipa = new File("D:\\Info.ipa");
		ZipFile zipFile = new ZipFile(ipa);
		ZipEntryFinder entryFinder = new ZipEntryFinder();
		ZipEntry ze = entryFinder.findZipEntry(zipFile, "Info.plist");
		FileUtils.writeByteArrayToFile(f, IOUtils.toByteArray(zipFile.getInputStream(ze)));
		PropertyListParser.convertToXml(f, nf);
		zipFile.close();
		System.out.println(UUID.randomUUID());
		System.out.println("ipa size : "+ipa.length());
		System.out.println("done..");
	}

}
