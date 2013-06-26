package com.akwolf.rbac.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.akwolf.rbac.model.PagerModel;
import com.akwolf.rbac.model.User;
import com.akwolf.rbac.persistence.UserMapper;


public class UserTest extends TestObject{
	
	
	
	@Test
	public void testPagination(){
		UserMapper userMapper = session.getMapper(UserMapper.class) ;
		
		Map<String,Object> condition = new HashMap<String, Object>() ;
		condition.put("start", 0) ;
		condition.put("end", 10) ;
		PagerModel<User> pm = userMapper.paginationUser(condition) ;
		System.out.println(pm.getTotal()+"--"+pm.getRows().size());
		
		List<User> list = pm.getRows() ;
		for (User user : list) {
			System.out.println(user);
		}
		
	}

}
