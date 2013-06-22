package com.childe.san.upload ;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.childe.san.upload.FileUploadTools.UploadMsg;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response) ;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileUploadTools tool = null;
		try {
			// 初始化上传工具类
			tool = new FileUploadTools(request);
			// 获取表单中文本框的内容
			String name = tool.getParameter("name") ;
			// 设置运行上传的后缀名,不设置则运行上传任意类型的文件
			tool.setAllowedExts(new String[]{"txt","avi","ipa","jpg"}) ;
			// 保存上传文件，获取上传后的信息[存储名，大小]
			Map<String, UploadMsg> uploadMsg = tool.saveFileRandomName() ;
			// 通过表单中key取得上传信息
			UploadMsg msg = uploadMsg.get("uploadFile") ;
			// Test
			System.out.println("表单内容："+name+",上传后的信息是："+msg);
			response.getWriter().write("表单内容："+name+",上传后的信息是："+msg) ;
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnAllowedExtException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
