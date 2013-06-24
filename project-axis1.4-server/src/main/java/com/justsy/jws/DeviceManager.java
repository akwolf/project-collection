package com.justsy.jws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * 
 * 文件名 : DeviceManager.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-3-4
 */
@WebService
public interface DeviceManager {

	@WebMethod
	public String listDevices(@WebParam(name = "encryptStr") String encryptStr);

	@WebMethod
	public String deleteDevice(
			@WebParam(name = "encryptStr") String encryptStr,
			@WebParam(name = "deviceId") String deviceId);

	@WebMethod
	public String deviceAction(
			@WebParam(name = "encryptStr") String encryptStr,
			@WebParam(name = "deviceId") String deviceId,
			@WebParam(name = "deviceAction") String deviceAction);

	@WebMethod
	public String deviceLogs(@WebParam(name = "encryptStr") String encryptStr,
			@WebParam(name = "deviceId") String deviceId);
}
