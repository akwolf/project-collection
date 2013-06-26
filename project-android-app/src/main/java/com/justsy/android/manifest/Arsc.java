package com.justsy.android.manifest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import brut.androlib.AndrolibException;
import brut.androlib.res.data.ResConfig;
import brut.androlib.res.data.ResID;
import brut.androlib.res.data.ResPackage;
import brut.androlib.res.data.ResResSpec;
import brut.androlib.res.data.ResResource;
import brut.androlib.res.decoder.ARSCDecoder;
import brut.androlib.res.decoder.ARSCDecoder.ARSCData;
import brut.androlib.res.decoder.ARSCDecoder.FlagsOffset;

/**
 * 
 * 
 * 文件名 : Arsc.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2012-11-29
 */
public class Arsc {
	public static void main(String[] args) throws Exception {
		// ZipOutputStream out = null;
		// String tag = null;
		File inFile = new File("D:\\UCBrowser.apk");
		ZipFile zip = new ZipFile(inFile);
		ZipEntry entry = zip.getEntry("resources.arsc");
		InputStream in = zip.getInputStream(entry);
		byte[] data = IOUtils.toByteArray(in);
		ARSCData arsc = ARSCDecoder.decode(new ByteArrayInputStream(data),
				true, true);
		publicizeResources(data, arsc.getFlagsOffsets());

		System.out.println("--------ResPackage--------");
		
		System.out.println(arsc.getResTable().listMainPackages().size()) ;
		System.out.println(arsc.getResTable().listFramePackages().size()) ;
		
		
		
		System.out.println(arsc.getPackages()[0]+"---"+arsc.getPackages()[1]);
		
		
		//ResPackage rp = arsc.getOnePackage();
		ResPackage rp = arsc.getPackages()[1];
		
		
		
		List<ResConfig> list = rp.getConfigs();
		System.out.println();
		System.out.println("--------ResConfig--------");
		System.out.println("ResConfig : " + list);

		for (ResConfig resConfig : list) {
			 System.out.println("flag : "+resConfig.getFlags()+" , ResSpecs : "+resConfig.listResSpecs());
		}

		System.out.println("--------ResConfig--------");
		System.out.println();
		System.out.println("name : " + rp.getName());
		System.out.println("--------ResPackage--------");

		System.out.println("--------listFiles--------");
		System.out.println("files : " + rp.listFiles());
		Set<ResResource> rrs = rp.listFiles();
		for (ResResource resResource : rrs) {
			System.out.println("config : " + resResource.getConfig()
					+ " ,value : " + resResource.getValue() + " , ResSpec : "
					+ resResource.getResSpec() + " ,path : "
					+ resResource.getFilePath());
		}

		System.out.println("--------listFiles--------");

		System.out.println("--------ResResSpec--------");
		List<ResResSpec> rrss = rp.listResSpecs();
		for (ResResSpec resResSpec : rrss) {
			if ("reader_app_android".equals(resResSpec.getName())) {
				ResID rID = resResSpec.getId() ;
				System.out.println("rID : "+rID.entry+" , "+rID.id+" , "+rID.package_+" , "+rID.type+" ,toString :"+rID);
				
				System.out.println("fullName : " + resResSpec.getFullName()
						+ ", name : " + resResSpec.getName() + ", id : "
						+ resResSpec.getId() + ", type : "
						+ resResSpec.getType() + ", package : "
						+ resResSpec.getPackage() + ", resource : "
						+ resResSpec.listResources() + ", defaultResult : "
						+ resResSpec.hasDefaultResource());

			}
		}
		System.out.println("--------ResResSpec--------");

		/*
		 * System.out.println("Types : " + rp.listTypes());
		 * 
		 * Collection<ResValuesFile> resValuesFile = rp.listValuesFiles(); for
		 * (ResValuesFile resValuesFile2 : resValuesFile) { //
		 * System.out.println
		 * ("resValuesFile2 : "+resValuesFile2.listResources()); }
		 * 
		 * System.out.println(arsc.getOnePackage().getId());
		 * System.out.println(arsc.getOnePackage().getResTable());
		 * 
		 * File outFile = new File(getFrameworkDir(), String.valueOf(arsc
		 * .getOnePackage().getId()) + (tag == null ? "" : '-' + tag) + ".apk");
		 * 
		 * out = new ZipOutputStream(new FileOutputStream(outFile));
		 * out.setMethod(ZipOutputStream.STORED); CRC32 crc = new CRC32();
		 * crc.update(data); entry = new ZipEntry("resources.arsc");
		 * entry.setSize(data.length); entry.setCrc(crc.getValue());
		 * out.putNextEntry(entry); out.write(data);
		 * 
		 * // /C:\Users\zhangh\apktool\framework
		 * 
		 * System.out.println(getFrameworkDir());
		 */
	}

	private static void publicizeResources(byte[] arsc,
			FlagsOffset[] flagsOffsets) throws AndrolibException {
		for (FlagsOffset flags : flagsOffsets) {
			int offset = flags.offset + 3;
			int end = offset + 4 * flags.count;
			while (offset < end) {
				arsc[offset] |= (byte) 0x40;
				offset += 4;
			}
		}
	}

	// private static File getFrameworkDir() throws AndrolibException {
	// String path;
	//
	// /* store in user-home, for Mac OS X */
	// if (System.getProperty("os.name").equals("Mac OS X")) {
	// path = System.getProperty("user.home") + File.separatorChar
	// + "Library/apktool/framework";
	// } else {
	// path = System.getProperty("user.home") + File.separatorChar
	// + "apktool" + File.separatorChar + "framework";
	// }
	// File dir = new File(path);
	// if (!dir.exists()) {
	// if (!dir.mkdirs()) {
	// throw new AndrolibException("Can't create directory: " + dir);
	// }
	// }
	// return dir;
	// }
}
