package com.justsy.jws.client;

import com.justsy.push.PushMessageImplService_PortType;
import com.justsy.push.PushMessageImplService_Service;
import com.justsy.push.PushMessageImplService_ServiceLocator;

/**
 * iOS Push Message Demo
 * 
 * 文件名 : PushMain.java
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com	
 * @create date : 2013-3-5		
 */
public class PushMain {

	public static void main(String[] args) throws Exception {
		PushMessageImplService_Service implService = new PushMessageImplService_ServiceLocator() ;
		PushMessageImplService_PortType portType = implService.getPushMessageImplPort() ;
		// 通过设备列表取得的Toke
		String ret = portType.pushMessage("0a16339b7221c7fd006bef3eade4a3328bfd5452325787e543c3a97088883db5", "I love xiaojiang !!", 0, 1) ;
		System.out.println(ret);
		
	}

}
