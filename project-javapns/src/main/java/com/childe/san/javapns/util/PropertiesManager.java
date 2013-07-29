package com.childe.san.javapns.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesManager {
	protected static final Logger logger = Logger.getLogger(PropertiesManager.class);
	private static PropertiesManager instance;
	private static Properties PROPS;

	public static PropertiesManager getInstance() {
		if (instance == null)
			synchronized (PropertiesManager.class) {
				instance = new PropertiesManager();
			}
		return instance;
	}

	public PropertiesManager() {
		logger.info("-----PropertiesManager构建--------");
		PROPS = new Properties();
		try {
			PROPS.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath()
					+ "config.properties"));
		} catch (FileNotFoundException localFileNotFoundException) {
			logger.info("-------找不到指定的属性文件------" + localFileNotFoundException.getMessage());
			localFileNotFoundException.printStackTrace();
		} catch (IOException localIOException) {
			logger.info("-------读取文件是出错！------" + localIOException.getMessage());
			localIOException.printStackTrace();
		}
	}

	public String getValueByPropertyKey(String paramString) {
		logger.info("----key:----" + paramString);
		logger.info("----value:----" + PROPS.getProperty(paramString));
		return PROPS.getProperty(paramString);
	}

	public static void main(String[] paramArrayOfString) {
		System.out.println(PropertiesManager.class.getClass().getResource("/").getPath());
		System.out.println(PropertiesManager.class.getClassLoader());
		System.out.println(getInstance().getValueByPropertyKey("fileName"));
		System.out.println(getInstance().getValueByPropertyKey("filePassword"));
	}
}