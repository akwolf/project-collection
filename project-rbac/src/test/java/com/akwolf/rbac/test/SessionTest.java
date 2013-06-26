package com.akwolf.rbac.test;

import org.junit.Test;

import com.akwolf.rbac.model.Session;
import com.akwolf.rbac.persistence.SessionMapper;


public class SessionTest extends TestObject{

	private SessionMapper sessionMapper ;
	
	@Test
	public void testGetSession(){
		sessionMapper = session.getMapper(SessionMapper.class)  ;
		
		Session session = sessionMapper.getSessionBySessionId("4d33ad84-44d2-4391-95a0-80b64c1b815f") ;
		
		System.out.println(session);
	}
}
