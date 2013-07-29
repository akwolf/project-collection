package com.childe.san.javapns;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.Devices;

import org.apache.log4j.Logger;

import com.childe.san.javapns.util.EncryptManager;
import com.childe.san.javapns.util.PropertiesManager;

public class PushByAPNS {
	protected static final Logger logger = Logger.getLogger(PushByAPNS.class);
	public static String KEYSTORE = Thread.currentThread().getContextClassLoader().getResource("").getPath()
			+ PropertiesManager.getInstance().getValueByPropertyKey("fileName");
	public static String KEYPASSWORD = EncryptManager.getInstance().decodeBase64String(
			PropertiesManager.getInstance().getValueByPropertyKey("filePassword"));

	public static String pushMessageToAPNS(String token, String message, int badge, int pushType)
			throws CommunicationException {
		logger.info("----KEYSTORE:---" + KEYSTORE);
		String str = "消息推送成功！";
		try {
			logger.info("-----Token:-----" + token + "----message:-----" + message + "----badge:----"
					+ String.valueOf(badge) + "-------pushType:------" + String.valueOf(pushType)
					+ "------KEYSTORE:-------" + KEYSTORE + "------KEYPASSWORD:-------" + KEYPASSWORD);
			if (pushType == 0)
				Push.badge(badge, KEYSTORE, KEYPASSWORD, true, getPushDevices(token));
			else
				Push.combined(message, badge, "Default", KEYSTORE, KEYPASSWORD, true, getPushDevices(token));
		} catch (KeystoreException localKeystoreException) {
			logger.info("证书信息有误：" + localKeystoreException.getMessage());
			str = "证书信息有误：" + localKeystoreException.getMessage();
		}
		return str;
	}

	public static List<Device> getPushDevices(String paramString) {
		return Devices.asDevices(paramString);
	}
}