package com.akwolf.rbac.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class JavaTest {
	
	
	@Test
	public void testSplit(){
		//System.out.println("\r\n"+"--");
		
		System.out.println(isAllowed("/system/main/index")); ;
	}
	
	
	public boolean isAllowed(String actionUrl) {
			String urls = "/system/.*";
			if (urls == null || urls.trim().equals("")){
				return false;
			}
			for(String url: urls.split("\r\n")){
				Pattern p = Pattern.compile(url, Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(actionUrl);
				if (m.find()){
					return true;
				}
			}
		return false;
	}
}
