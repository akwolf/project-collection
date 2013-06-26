package com.akwolf.rbac.persistence;

import java.util.Map;

import com.akwolf.rbac.model.PagerModel;
import com.akwolf.rbac.model.User;

public interface UserMapper {

	/**
	 * 通过用户名密码取得用户
	 * 
	 * @param map
	 * @return
	 */
	public User getUserByNameAndPassword(Map<String, String> map);

	/**
	 * 分页显示用户
	 * 
	 * @param condition
	 * @return
	 */
	public PagerModel<User> paginationUser(Map<String, Object> condition);
}
