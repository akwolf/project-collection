package com.childe.san.dom4j.offline;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

/**
 * 
 * 
 * 文件名 : ValidateXml.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2012-11-22
 */
public class ValidateXml {

	public static void main(String[] args) throws DocumentException {
		 Document doc = new ValidateXml().loadXml() ;
		 System.out.println(doc.getRootElement().getName());
	}
	

	public Document loadXml() throws DocumentException {
		InputSource input = new InputSource(this.getClass().getClassLoader().getResourceAsStream("angryInfo.plist")) ;		
		SAXReader saxReader = new SAXReader();		
		saxReader.setEntityResolver(new OfflineEntityResover(input.getSystemId())) ;
		Document doc = saxReader.read(input);
		return doc;
	}
}
