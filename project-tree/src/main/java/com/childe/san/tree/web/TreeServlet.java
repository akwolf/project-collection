package com.childe.san.tree.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.childe.san.tree.dao.TreeDao;
import com.childe.san.tree.dto.TreeNode;

public class TreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		TreeDao dao = new TreeDao();
		String action = request.getParameter("action");
		if (action.equals("root")) {
			try {
				TreeNode tn = dao.rootTree();
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JSON.toJSONString(new Object[] { tn }));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (action.equals("child")) {
			int pId = Integer.parseInt(request.getParameter("id"));
			try {
				List<TreeNode> list = dao.adjacencyList(pId);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JSON.toJSONString(list));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (action.equals("insert")) {
			//{"status":200,"msgs":{"id":xx}}
			int pId = Integer.parseInt(request.getParameter("pId"));
			String text = request.getParameter("text");
			try {
				long i = dao.insertTreeNode(pId, text);
				Map<String, Object> res = new HashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", i);
				res.put("status", 200);
				res.put("msgs", map);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JSON.toJSONString(res));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				dao.deleteTreeNode(id);
				Map<String, Object> res = new HashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				res.put("status", 200);
				res.put("msgs", map);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JSON.toJSONString(res));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if (action.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String text = request.getParameter("text");
			try {
				dao.updateTreeNode(id, text) ;
				Map<String, Object> res = new HashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				res.put("status", 200);
				res.put("msgs", map);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(JSON.toJSONString(res));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
