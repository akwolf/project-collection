package com.justsy.jws;

import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * 文件名 : DeviceManagerImpl.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-3-4
 */
@WebService(endpointInterface = "com.justsy.jws.DeviceManager")
public class DeviceManagerImpl implements DeviceManager {
	private Logger logger = Logger.getLogger(getClass());

	//	@Override
	public String listDevices(String encryptStr) {
		return encryptStr;
	}

	//	@Override
	public String deleteDevice(String encryptStr, String deviceId) {
		System.out.println(encryptStr);
		return encryptStr;
	}

	//	@Override
	public String deviceAction(String encryptStr, String deviceId, String deviceAction) {
		System.out.println(encryptStr);
		return encryptStr;
	}

	//	@Override
	public String deviceLogs(String encryptStr, String deviceId) {
		System.out.println(encryptStr);
		return encryptStr;
	}

}
