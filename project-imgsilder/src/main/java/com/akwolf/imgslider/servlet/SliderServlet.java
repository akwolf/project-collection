package com.akwolf.imgslider.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.akwolf.imgslider.dao.SliderDao;
import com.akwolf.imgslider.model.Slider;

/**
 * 读取数据中图片的接口
 * 
 * @author zhangh
 *
 */
public class SliderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(getClass());

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		try {
			SliderDao dao = new SliderDao();
			List<Slider> list = dao.getImgs();
			StringBuffer buffer = new StringBuffer();
			buffer.append("<root>");
			for (Slider slider : list) {
				buffer.append("<row id=\"" + slider.getId() + "\" path=\"" + reader.getValue("server.url") + "/"
						+ slider.getName() + "\" />");
			}
			buffer.append("</root>");
			response.setContentType("text/xml");
			logger.debug("response msg : " + buffer.toString());

			response.getWriter().write(buffer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
