package com.justsy.android.manifest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import brut.androlib.AndrolibException;
import brut.androlib.res.data.ResPackage;
import brut.androlib.res.data.ResResSpec;
import brut.androlib.res.decoder.ARSCDecoder;
import brut.androlib.res.decoder.ARSCDecoder.ARSCData;
import brut.androlib.res.decoder.ARSCDecoder.FlagsOffset;

/**
 * 解析android Arsc文件
 * 
 * 文件名 : AndroidArsc.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2012-11-30
 */
public class AndroidArsc {

	private ResPackage rp;

	public AndroidArsc(InputStream in, String appPackage) throws IOException, AndrolibException {
		this.rp = updateResPackage(in, appPackage);
	}

	private ResPackage updateResPackage(InputStream in, String apkPackage) throws AndrolibException, IOException {
		byte[] data = IOUtils.toByteArray(in);
		ARSCData arsc = ARSCDecoder.decode(new ByteArrayInputStream(data), true, true);
		publicizeResources(data, arsc.getFlagsOffsets());
		ResPackage rp = null;
		if (arsc.getPackages().length > 1) {
			ResPackage[] rps = arsc.getPackages();
			for (ResPackage resPackage : rps) {
				if (resPackage.getName().equals(apkPackage)) {
					rp = resPackage;
				}
			}
		} else {
			rp = arsc.getOnePackage();
		}
		return rp;
	}

	/**
	 * 通过常量取值
	 * 
	 * @param id
	 * @return
	 */
	public String getResSpecValue(String id) {
		List<ResResSpec> rrss = rp.listResSpecs();
		String rID = null;
		for (ResResSpec resResSpec : rrss) {
			rID = resResSpec.getId().toString();
			rID = rID.substring(2);
			if (rID.equalsIgnoreCase(id)) {
				return resResSpec.getName();
			}
		}
		return null;
	}

	private void publicizeResources(byte[] arsc, FlagsOffset[] flagsOffsets) throws AndrolibException {
		for (FlagsOffset flags : flagsOffsets) {
			int offset = flags.offset + 3;
			int end = offset + 4 * flags.count;
			while (offset < end) {
				arsc[offset] |= (byte) 0x40;
				offset += 4;
			}
		}
	}

	public void setArscInputStream(InputStream in, String apkPackage) throws AndrolibException, IOException {
		this.rp = updateResPackage(in, apkPackage);
	}
}
