package com.akwolf.rbac.controllers;

import java.util.ArrayList;
import java.util.List;

import com.akwolf.framework.extention.JsonPluginView;
import com.alibaba.fastjson.JSONObject;
import com.et.mvc.View;

/**
 * @author zhanghua
 * 
 *         加载按钮的控制器
 * 
 */
public class MenuController extends ApplicationController {

	public View listMenu() {
		// 菜单栏
		JSONObject menuBar = new JSONObject() ;
		// 所有一级菜单
		List<JSONObject> mainMenuList = new ArrayList<JSONObject>() ;
		// 二级餐单
		JSONObject mainMenu = new JSONObject() ;
		// 二级菜单中添加要显示的内容
		mainMenu.put("menuid", "1") ;
		mainMenu.put("menuname", "控件使用") ;
		mainMenu.put("icon", "icon-sys") ;
		
		// 具体餐单的容器
		List<JSONObject> itemList = new ArrayList<JSONObject>() ;
		
		
		// 每个具体菜单
		JSONObject item = new JSONObject() ;
		item.put("menuid", "12") ;
		item.put("menuname", "疯狂秀才") ;
		item.put("icon", "icon-add") ;
		item.put("url", "http://hxling.cnblogs.com") ;
		// 菜单容器中添加具体菜单
		itemList.add(item) ;
		
		//------item 每个具体的餐单按钮--//
		item = new JSONObject() ;
		item.put("menuid", "13") ;
		item.put("menuname", "用户管理") ;
		item.put("icon", "icon-users") ;
		item.put("url", "http://www.baidu.com") ;
		itemList.add(item) ;
		//------item 每个具体的餐单按钮--//
		item = new JSONObject() ;
		item.put("menuid", "14") ;
		item.put("menuname", "角色管理") ;
		item.put("icon", "icon-role") ;
		item.put("url", "http://www.iteye.com") ;
		itemList.add(item) ;
		//----第三个餐单选项---//
		
		//end ----第三个餐单选项---//
		
		// 二级菜单中添加菜单容器
		mainMenu.put("menus", itemList) ;
		// 一级菜单中添加二级餐单
		mainMenuList.add(mainMenu) ;
		
		// 菜单栏中添加一级菜单
		menuBar.put("menus", mainMenuList) ;

		System.out.println(menuBar);
		return new JsonPluginView(menuBar);
	}
}
