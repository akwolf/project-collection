package com.justsy.android.manifest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * @author zhangh
 * @createTime 2013-6-25 下午7:52:52
 */
public class AsrcMain {

	/**
	 * @param args
	 * @throws FoundException 
	 */
	public static void main(String[] args) throws Exception {
		File inFile = new File("D:\\AndroidManifest-New.xml");
		FileInputStream in = new FileInputStream(inFile);

		StringBuffer buffer = new StringBuffer(8);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}

		System.out.println(buffer.toString());
		
		br.close();
		in.close();
		FileInputStream arscIn = new FileInputStream(new File("D:\\resources.arsc"));
		Document doc = DocumentHelper.parseText(buffer.toString());
		Element root = doc.getRootElement();
		String apkPackage = root.attributeValue("package");
		Element application = root.element("application");
		Namespace ns = Namespace.get("android", "http://schemas.android.com/apk/res/android");
		AndroidArsc aa = new AndroidArsc(arscIn, apkPackage);
		String iconAttr = application.attributeValue(QName.get("icon", ns));

		String iconName = null;
		if (iconAttr.startsWith("@") && iconAttr.lastIndexOf("/") < 0) {
			iconName = aa.getResSpecValue(iconAttr.substring(1));
		} else {
			iconName = iconAttr.substring(iconAttr.lastIndexOf("/") + 1);
		}
		iconName = iconName + ".png";
		arscIn.close();
		System.out.println(iconName);
	}
}
