package com.justsy.jws.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.justsy.jws.DeviceManager;
import com.justsy.jws.DeviceManagerImplService;
import com.justsy.jws.DeviceManagerImplServiceLocator;
import com.justsy.jws.encrypt.Encrypt;

/**
 * 设备管理调用Demo
 * 
 * 文件名 : Main.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-3-5
 */
public class Main {

	public static void main(String[] args) throws Exception {
		DeviceManagerImplService serviceLocator = new DeviceManagerImplServiceLocator();
		DeviceManager deviceManager = serviceLocator.getDeviceManagerImplPort();

		String timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date());
		//Encrypt encrypt = new Encrypt();

		String userName = "mdmtest1";
		String password = "Pass@word1";

		String authInfo = Encrypt.encode3DES(userName + "|" + password + "|"
				+ timestamp);

		// String authInfo = java.net.URLEncoder.encode(Encrypt
		// .encode3DES("mdmtest1|Pass@word1|"
		// + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
		// .format(new Date())));
		// 设备列表
		// String ret = deviceManager.listDevices(authInfo) ;
		String ret = deviceManager.listDevices(authInfo);
		System.out.println(ret);
		System.out.println(authInfo);

	}

}
