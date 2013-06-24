package com.childe.san.pullxml;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

public class TestXml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PullParseXml parseXml = new PullParseXml() ;
		
		List<Person> list;
		try {
			list = parseXml.parse(parseXml.getInput());
			for (Person person : list) {
				System.out.println(person.toString());
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
