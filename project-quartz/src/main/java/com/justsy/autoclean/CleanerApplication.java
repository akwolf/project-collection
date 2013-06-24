package com.justsy.autoclean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.justsy.autoclean.util.PropertiesReader;

/**
 * 
 * 
 * 文件名 : CleanerApplication.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-4-16
 */
public class CleanerApplication {
	private static final Logger logger = Logger
			.getLogger(CleanerApplication.class);
	private SchedulerFactory factory;
	Scheduler sched;

	public CleanerApplication() throws IOException, SchedulerException {
		Properties p = new Properties();
		p.load(PropertiesReader.class.getClassLoader().getResourceAsStream(
				"quartz.properties"));
		factory = new StdSchedulerFactory(p);
		sched = factory.getScheduler();

	}

	public static void main(String[] args) {
		try {
			CleanerApplication ca = new CleanerApplication();
			logger.info("Application main begin exec,with args{}!"
					+ Arrays.toString(args));
			int argsLen = args.length;
			if ((argsLen > 0) && ("start".equals(args[0].trim()))) {
				ca.start();
			} else {
				ca.stop();
			}
		} catch (IOException e) {
			logger.error("读取quartz.properties资源文件IO出错", e);
		} catch (SchedulerException e) {
			logger.error("任务调度出错", e);
		} catch (Exception e) {
			logger.error("unkown error!!", e);
		}

	}

	private void start() throws SchedulerException {
		sched.start();
		logger.debug("Scheduler Name : " + sched.getSchedulerName());
	}

	private void stop() throws SchedulerException {
		sched.shutdown(true);
	}

}
