package com.akwolf.imgslider.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.akwolf.imgslider.util.PropertiesReader;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(getClass());
	
	protected PropertiesReader reader = PropertiesReader.getInstance() ;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected abstract void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

}
