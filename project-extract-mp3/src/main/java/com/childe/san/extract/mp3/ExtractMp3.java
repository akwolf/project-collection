package com.childe.san.extract.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class ExtractMp3 {

	public static void main(String[] args) {
		new ExtractMp3().getMusicMsg(new File("F:\\DMDownLoad\\Song\\SongTaste-爆好听的女声 What It Is.mp3"));
	}

	private RandomAccessFile ran = null;
	private File file = null;

	public ExtractMp3() {
	}
	
	public ExtractMp3(File file) throws FileNotFoundException {
		super();
		setFile(file);
		System.out.println(file.length() + "字节");
		System.out.println(((double) file.length()) / (1024 * 1024)+"MB");
		ran = new RandomAccessFile(file, "r");
		System.out.println("文件装载完毕");
	}

	/**
	 * 
	 * 获取音乐的详细信息并且保存在map中
	 * 
	 * @param file
	 * @return 返回类型说明
	 */
	public Map<String, String> getMusicMsg(File file) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			ExtractMp3 read = new ExtractMp3(file);
			byte[] buffer = new byte[128];
			read.ran.seek(read.ran.length() - 128);
			read.ran.read(buffer);
			SongInfo info = new SongInfo(buffer);
			System.out.println("Name:" + info.getSongName() + ";artist:"
					+ info.getArtist() + ";album:" + info.getAlbum());
			map.put("musicname", info.getSongName());
			map.put("musicauthor", info.getArtist());
			map.put("musicalbum", info.getAlbum());
			System.out.println(info);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}
