package com.akwolf.rbac.controllers;

import com.akwolf.rbac.RbacService;
import com.et.mvc.Controller;
import com.et.mvc.binding.DataBinders;
import com.et.mvc.filter.BeforeFilter;

@BeforeFilter(execute = "auth")
public class ApplicationController extends Controller {
	static {
		DataBinders.setAllowEmpty(true);
	}

	protected RbacService rbac;

	protected boolean auth() throws Exception {
//		SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
//		rbac = new DefaultRbacServiceImpl(request, sqlSession);
//		if (rbac.getCurrentUser() == null) {
//			redirect("/home/login");
//			return false;
//		}
//		request.setAttribute("rbac", rbac);

		return true;
	}
}
