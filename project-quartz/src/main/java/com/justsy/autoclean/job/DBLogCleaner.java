package com.justsy.autoclean.job;

import java.io.File;
import java.io.IOException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 
 * 文件名 : DBLogCleaner.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2013-4-16
 */
public class DBLogCleaner extends AbstractFileCleaner {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String runDir = System.getProperty("user.dir");
		File f = new File(runDir);
		File pf = f.getParentFile();
		f = new File(pf, propertiesReader.getValue("bat.path"));
		logger.debug("bat file path : " + f.getPath());
		try {
			logger.debug("begin exec bat!!  ");
			Process p = Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/c", f.getPath() });
			int ret = p.waitFor();
			logger.debug("end exec bat, return " + ret);
		} catch (IOException e) {
			logger.error("执行run.bat文件出错", e);
		} catch (InterruptedException e) {
			logger.error("命令行InterruptedException", e);
		}
		logger.debug("数据库日志重做完成");
	}

}
