package com.akwolf.imgslider.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.akwolf.imgslider.dao.SliderDao;

/**
 * tomcat容器监听器
 * 
 * @author zhangh
 *
 */
public class ApplicationListener implements ServletContextListener {

	// 每次对项目修改都会在tomcat中重新部署，将之前上传的图片清除，与之也要将数据库中的数据清除
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		SliderDao dao = new SliderDao() ;
		try {
			dao.clearImgs() ;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

	}

}
