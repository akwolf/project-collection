package com.akwolf.rbac.test;

import java.lang.reflect.Method;

import org.junit.Test;


public class Reflect {
	
	
	@Test
	public void test(){
		
		Method[] me = Son.class.getMethods() ;
		
		
		Method[] m = Son.class.getDeclaredMethods() ;
		for (Method method : me) {
			System.out.println(method.getName());
		}
		
		
		System.out.println("----------------");
		
		for (Method method : m) {
			System.out.println(method.getName());
		}
	}

}
