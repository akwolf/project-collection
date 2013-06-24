package com.childe.san.pullxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullParseXml {
	
	
	public InputStream getInput(){
		InputStream in = getClass().getClassLoader().getResourceAsStream("persons.xml") ;
		
		return in ;
	} 
	
	public List<Person> parse(InputStream in) throws XmlPullParserException, IOException{
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();		
		List<Person> list = null ;
		Person person = null ;
		
		parser.setInput(in, "UTF-8") ;	//解析xml文件
		
		int eventType = parser.getEventType() ;
		
		String tag = null ;
		
		while(eventType!=XmlPullParser.END_DOCUMENT){
			
			tag = parser.getName() ;
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<Person>() ;
				break ;
			case XmlPullParser.START_TAG:
				if("person".equals(tag)){
					person = new Person() ;
					person.setId(Integer.parseInt(parser.getAttributeValue(0))) ;
				}
				if(person!=null){
					if("name".equals(tag)){
						person.setName(parser.nextText()) ;
					}
					if("age".equals(tag)){
						person.setAge(Integer.parseInt(parser.nextText())) ;
					}
				}
				break;
			case XmlPullParser.END_TAG :
				if("person".equals(tag)){
					list.add(person) ;
					person = null ;
				}
				break ;
			}
			
			eventType = parser.next() ;	//向下移动
		}
		return list ;
		
	}
	
	
	
}
