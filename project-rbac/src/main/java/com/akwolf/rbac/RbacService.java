package com.akwolf.rbac;

import java.util.Map;

import com.akwolf.rbac.model.User;

public interface RbacService {
	/**
	 * 当前用户是否存在
	 * 
	 * @return 已登录存在返回true，否则返回false
	 * @throws RbacException
	 */
	public boolean exists() throws RbacException;

	/**
	 * 登录系统
	 * 
	 * @param login
	 *            登录名
	 * @param pass
	 *            密码
	 * @return 登录成功时返回{success:true,sessionId:xxx}<br/>
	 *         失败时返回{failure:true,errors:xxx}
	 * @throws RbacException
	 */
	public Map<String, Object> login(String login, String pass) throws RbacException;

	/**
	 * 退出系统，注销当前用户
	 * 
	 * @throws RbacException
	 */
	public void logout() throws RbacException;

	/**
	 * 某个资源访问路径是否被允许的
	 * 
	 * @param actionUrl
	 *            操作动作的URL，如/user/create，支持正则表达式
	 * @return 有权限返回true，否则返回false
	 * @throws RbacException
	 */
	public boolean isAllowed(String actionUrl) throws RbacException;

	/**
	 * 用户是否具有某个权限
	 * 
	 * @param privilegeName
	 *            权限名称
	 * @return 有权限返回true，否则返回false
	 * @throws RbacException
	 */
	public boolean hasPrivilege(int privilegeId) throws RbacException;

	/**
	 * 返回当前登录用户
	 * 
	 * @return
	 * @throws com.et.rbac.RbacException
	 */
	public User getCurrentUser() throws RbacException;
}
