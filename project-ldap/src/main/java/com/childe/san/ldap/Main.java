package com.childe.san.ldap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhangh
 * @createTime 2013-7-22 下午7:44:47
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		LdapHelper lh = new LdapHelper();
		Person p = lh.findPersonByUid("wangyifan012345");
		System.out.println(p);
		Group g = lh.findGroupById("8007");
		System.out.println(g);
		//		List<Group> list = lh.findAllGroups();
		List<Person> list = lh.findPersonsByDepartId("8007");
		ByteArrayInputStream bais = new ByteArrayInputStream(list.toString().getBytes());
		writeFile(bais);
		bais.close();
		lh.free();
	}

	protected static void writeFile(InputStream in) throws IOException {
		int tmp = 0;
		File file = new File("ldap.txt");
		FileOutputStream fos = new FileOutputStream(file);

		byte[] buffer = new byte[512];

		while ((tmp = in.read(buffer)) != -1) {
			fos.write(buffer, 0, tmp);
		}
		fos.close();
	}

}
