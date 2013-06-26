package com.akwolf.rbac.test ;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class TestObject {
	
	protected static SqlSession session ;
	
	@BeforeClass
	public static void before() throws IOException{
		Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml") ;
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader) ;
		session = factory.openSession() ;
	}
	
	@AfterClass
	public static void after(){
		session.commit() ;
		session.close() ;
	}
}
