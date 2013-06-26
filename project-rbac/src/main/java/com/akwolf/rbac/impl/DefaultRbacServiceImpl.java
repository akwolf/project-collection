package com.akwolf.rbac.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.akwolf.rbac.RbacException;
import com.akwolf.rbac.RbacService;
import com.akwolf.rbac.model.Privilege;
import com.akwolf.rbac.model.Role;
import com.akwolf.rbac.model.Session;
import com.akwolf.rbac.model.User;
import com.akwolf.rbac.persistence.SessionMapper;
import com.akwolf.rbac.persistence.UserMapper;
import com.akwolf.rbac.util.PropertyUtil;

public class DefaultRbacServiceImpl implements RbacService {

	private HttpServletRequest request;

	private SqlSession sqlSession;

	public DefaultRbacServiceImpl(HttpServletRequest request, SqlSession sqlSession) {
		this.request = request;
		this.sqlSession = sqlSession;
	}

	@Override
	public boolean exists() throws RbacException {
		return getCurrentUser() != null;
	}

	@Override
	public Map<String, Object> login(String login, String pass) throws RbacException {
		this.logout();

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, String> param = new HashMap<String, String>();
		param.put("login", login);
		param.put("password", pass);

		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUserByNameAndPassword(param);

		if (user != null) {
			SessionMapper sessionMapper = sqlSession.getMapper(SessionMapper.class);
			Session s = PropertyUtil.setProperty(Session.class, "setUser",
					PropertyUtil.setProperty(User.class, "setId", user.getId()));
			sessionMapper.addSession(s);
			map.put("result", "success");
			map.put("user", user);
		} else {
			map.put("result", "failure");
			map.put("error", "用户名错误或密码错误！！");
		}

		return map;
	}

	@Override
	public void logout() throws RbacException {
		SessionMapper sessionMapper = sqlSession.getMapper(SessionMapper.class);
		String sessionId = getSessionId();
		if (sessionId != null) {
			sessionMapper.deleteSessionBySessionId(sessionId);
		}
	}

	@Override
	public boolean isAllowed(String actionUrl) throws RbacException {
		List<Privilege> privileges = getUserPrivileges();
		for (Privilege privilege : privileges) {
			String url = privilege.getUrls();
			String[] u = url.split("\r\n");
			for (String string : u) {
				Pattern pattern = Pattern.compile(string);
				Matcher matcher = pattern.matcher(actionUrl);
				if (matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasPrivilege(int privilegeId) throws RbacException {
		List<Privilege> list = getUserPrivileges();
		for (Privilege privilege : list) {
			if (privilege.getId() == privilegeId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前角色的所有权限
	 * 
	 * @return
	 * @throws RbacException
	 */
	private List<Privilege> getUserPrivileges() throws RbacException {
		User user = this.getCurrentUser();
		Set<Privilege> sets = new HashSet<Privilege>();
		if (user != null) {
			List<Role> roles = user.getRoles();
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {
					List<Privilege> ps = role.getPrivileges();
					for (Privilege privilege : ps) {
						sets.add(privilege);
					}
				}
			}
		}
		List<Privilege> privileges = new ArrayList<Privilege>(sets);

		return privileges;
	}

	@Override
	public User getCurrentUser() throws RbacException {
		String sessionId = getSessionId();
		if (sessionId == null) {
			return null;
		}

		SessionMapper sessionMapper = sqlSession.getMapper(SessionMapper.class);

		Session session = sessionMapper.getSessionBySessionId(sessionId);

		if (session == null) {
			return null;
		} else {
			return session.getUser();
		}
	}

	/**
	 * 获取客户端浏览器中存储的cookie
	 * 
	 * @return
	 */
	private String getSessionId() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("sessionId".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
