package com.childe.san.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 对zip包进行查证
 * 
 * 策略模式
 * 定义一些列的算法，将他们封装起来，可以相互置换
 * 
 * @author zhangh
 * @createTime 2013-7-4 下午5:13:48
 */
public class ZipEntryFinder {

	/**
	 * 正则的方式进行查找
	 * 
	 * @param zipFile
	 * @param pattern
	 * @return
	 */
	public List<ZipEntry> findZipEntryRegex(final ZipFile zipFile, final Pattern pattern) {

		if (pattern == null || zipFile == null) {
			throw new NullPointerException("pattern or zipFile can't null");
		}

		return getZipEntry(zipFile, new ZipFinderCallback() {
			List<ZipEntry> list = new ArrayList<ZipEntry>();

			public boolean isStop() {
				return false;
			}

			// 根据正则进行查找
			public void doFind(ZipEntry zipEntry) {
				Matcher matcher = pattern.matcher(zipEntry.getName());
				if (matcher.matches()) {
					list.add(zipEntry);
				}
			}

			public List<ZipEntry> findZipEntries() {
				return list;
			}
		});
	}

//	public File findZipFile(final ZipFile zipFile, final String fileName) {
//		ZipEntry ze = this.findZipEntry(zipFile, fileName) ;
//		
//	}

	/**
	 * 文件名的方式进行查证
	 * 
	 * @param zipFile
	 * @param fileName
	 * @return
	 */
	public ZipEntry findZipEntry(final ZipFile zipFile, final String fileName) {

		if (fileName == null || zipFile == null) {
			throw new NullPointerException("fileName or zipFile can't null");
		}

		return getZipEntry(zipFile, new ZipFinderCallback() {

			ZipEntry entry = null;

			// 当找到文件时，zipEntry不为空时结束查证
			public boolean isStop() {
				return entry != null;
			}

			// 定义查找的的逻辑
			public void doFind(ZipEntry zipEntry) {
				if (zipEntry != null) {
					if (fileName.equals(FilenameUtils.getName(zipEntry.getName()))) {
						entry = zipEntry;
					}
				}
			}

			public List<ZipEntry> findZipEntries() {
				List<ZipEntry> list = new ArrayList<ZipEntry>(1);
				list.add(entry);
				return list;
			}
		}).get(0);
	}

	// 定义zip搜索的步骤
	private interface ZipFinderCallback {
		// 终止查找的条件
		boolean isStop();

		// 返回查找的结果
		List<ZipEntry> findZipEntries();

		//定义查找的添加，执行查找的逻辑
		void doFind(ZipEntry zipEntry);

	}

	private List<ZipEntry> getZipEntry(final ZipFile zipFile, ZipFinderCallback callback) {
		Enumeration<ZipEntry> enumeration = zipFile.getEntries();
		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
			//			System.out.println(zipEntry.getName());
			// 执行查找
			callback.doFind(zipEntry);
			// 如果查找到，符合结束的条件
			if (callback.isStop()) {
				break;
			}
		}
		// 返回查找到的结果
		return callback.findZipEntries();
	}

	public static void main(String[] args) throws IOException {
		//abugfixer.xml
		File file = new File("D:\\Tencent\\676530129\\FileRecv\\EasService(1.0.0.1).zip");
		ZipFile zipFile = new ZipFile(file);
		ZipEntryFinder finder = new ZipEntryFinder();
		//		ZipEntry ze = finder.findZipEntry(zipFile, "abugfixer.xml");
		//		String name = ze.getName();

		//		System.out.println("find abugfixer.xml in zip name is : " + name);

		// 使用正则进行匹配
		//		List<ZipEntry> ls = finder.findZipEntryRegex(zipFile, Pattern.compile(".*menu_slidebar\\.9\\.png"));
		List<ZipEntry> ls = finder.findZipEntryRegex(zipFile, Pattern.compile(".*\\.exe|.*\\.iss"));
		System.out.println(ls);
	}
}
