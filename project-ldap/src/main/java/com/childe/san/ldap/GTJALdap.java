package com.childe.san.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

//import org.apache.log4j.Logger;

public class GTJALdap {

	private DirContext dc;

	//	private Logger logger = Logger.getLogger(getClass());

	public GTJALdap() {
		dc = this.getContext();
	}

	public static void main(String[] args) throws Exception {
		GTJALdap ct = new GTJALdap();
		// ct.getContext() ;
		ct.getData();
		// ct.getUserByUid("sunqian010024");
		// ct.getDepartInfo("0102", new Dept());// ,

	}

	//	public User getUserByUidSample(String uid) throws NamingException {
	//
	//		DirContext dc = this.getContext();
	//		User user = new User();
	//
	//		// 1、搜索的root
	//		String root = "dc=gtja,dc=net";
	//		// o=国泰君安证券股份有限公司,dc=orgusers,
	//
	//		// 2、设置查询过滤器
	//		String searchFilter = "(uid=" + uid + ")";
	//
	//		// 3、查询控制
	//		SearchControls ctrl = new SearchControls();
	//		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//
	//		NamingEnumeration enu = dc.search(root, searchFilter, ctrl);
	//		// System.out.println("enu:" + (enu.hasMore()));
	//		String[] attrs = { "uid", "dept1Code", "deptName", "mdevices", "cn" };
	//
	//		while (enu.hasMore()) {
	//			SearchResult sr = (SearchResult) enu.next();
	//			String dn = sr.getName() + "," + root;
	//			// Attributes ar = dc.getAttributes(dn, MY_ATTRS);
	//			// System.out.println(sr.getName());
	//		}
	//
	//		if (dc != null) {
	//			try {
	//				dc.close();
	//			} catch (NamingException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//
	//		return user;
	//	}

	// private User getUserFromAttr(Attribute attr){
	//
	// }

	/**
	 * 取得数据库
	 * 
	 * @throws Exception
	 */
	public void getData() throws Exception {
		// DirContext dc = this.getContext();
		String[] personKeys = { "uid", "cn", "mobile", "mail", "mdevices", "dept1Code" };
		String[] deptKeys = { "cn", "depGrade", "adminID", "depID" };
		String[] attrKeys = {};

		int type = 0;

		// String[] attrKeys = { "objectClass", "cn" };

		// 注意这里是baseDN的子树
		String root = "o=国泰君安证券股份有限公司,dc=orgusers,dc=gtja,dc=net";

		StringBuffer output = new StringBuffer();

		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration enu = dc.search(root, "objectClass=*", ctrl);

		//		Dept dept = null;

		while (enu.hasMore()) {
			SearchResult sr = (SearchResult) enu.next();
			//			dept = new Dept();
			// System.out.println(sr.getName());
			// System.out.println("toString:" + sr.toString());
			Attributes ab = sr.getAttributes();
			// Attribute attr = ab.get("objectClass").getAll();
			NamingEnumeration<?> objValues = ab.get("objectClass").getAll();
			while (objValues.hasMore()) {
				Object obj = objValues.next();
				if ("GtjaOrganization".equals(obj.toString())) {
					// 部门
					attrKeys = deptKeys;
					type = 1;
				} else if ("GtjaPerson".equals(obj.toString())) {
					attrKeys = personKeys;
					type = 0;
				}
			}

			// 获取需要取得的属性
			for (String string : attrKeys) {
				Attribute attr = ab.get(string);
				String value = null;
				if (attr != null) {
					value = attr.get().toString();
				}
				if (type == 0) {
					//					setLdapValue(string, value, dept);
					if ("dept1Code".equals(string)) {
						// System.out.println("--------------dept-----------");
						//						getDepartInfo(value, dept);
					}
				} else if (type == 1) {
					//					setLdapDeptValue(string, value, dept);
				}

				// System.out.println(string + "---->" + value);
				// if ("dept1Code".equals(string)) {
				// System.out.println("--------------dept-----------");
				// getDepartInfo(value);
				// }
			}
			//			dao.intsert(dept);
			// System.out.println(dept);
			System.out.println("====================================");

		}

		if (dc != null) {
			try {
				dc.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		//		dao.releaseConnQuietly();
		// System.out.println("export data Successfully!!");
	}

	//	private void setLdapDeptValue(String string, String value, Dept dept) {
	//		if ("depID".equals(string)) {
	//			dept.setGroupId(value);
	//		} else if ("cn".equals(string)) {
	//			dept.setGroupName(value);
	//		} else if ("depGrade".equals(string)) {
	//			dept.setGroupLevel(value);
	//		} else if ("adminID".equals(string)) {
	//			dept.setParentGroupID(value);
	//		}
	//
	//	}
	//
	//	private void setLdapValue(String string, String value, Dept dept) {
	//		if ("uid".equals(string)) {
	//			dept.setLdapUserName(value);
	//		} else if ("cn".equals(string)) {
	//			dept.setUserName(value);
	//		} else if ("mobile".equals(string)) {
	//			dept.setPhoneNum(value);
	//		} else if ("mail".equals(string)) {
	//			dept.setEmail(value);
	//		} else if ("mdevices".equals(string)) {
	//			dept.setDeviceNum(value == null ? 0 : Integer.parseInt(value));
	//		}
	//	}

	//	public Dept getUserByUid(String uid) throws NamingException, SQLException,
	//			IllegalArgumentException, IntrospectionException,
	//			InstantiationException, IllegalAccessException,
	//			InvocationTargetException, ClassNotFoundException {
	//
	//		// System.out.println("uid : " + uid);
	//
	////		LdapDao dao = new LdapDao();
	//		String[] personKeys = { "uid", "cn", "mobile", "mail", "mdevices",
	//				"dept1Code" };
	//
	//		// 注意这里是baseDN的子树
	//		String root = "o=国泰君安证券股份有限公司,dc=orgusers,dc=gtja,dc=net";
	//
	//		// StringBuffer output = new StringBuffer();
	//
	//		SearchControls ctrl = new SearchControls();
	//		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//
	//		// 设置使用uid进行查询
	//		String searchFilter = "(uid=" + uid + ")";
	//
	//		// 查询，并返回结果
	//		NamingEnumeration<SearchResult> enu = dc.search(root, searchFilter,
	//				ctrl);
	//
	//		Dept dept = null;
	//
	//		while (enu.hasMore()) {
	//			SearchResult sr = (SearchResult) enu.next();
	//			dept = new Dept();
	//
	//			// 获取当前行的所有属性
	//			Attributes ab = sr.getAttributes();
	//			// 获取人员拥有的属性
	//			for (String string : personKeys) {
	//				// 获取属性对象
	//				Attribute attr = ab.get(string);
	//				String value = null;
	//				if (attr != null) {
	//					// 获取属性值
	//					value = attr.get().toString();
	//					// 设置人员信息[属性，值，对象]
	//					setLdapValue(string, value, dept);
	//					// 得到人员的部门信息
	//					if ("dept1Code".equals(string)) {
	//						// System.out.println("--------------dept-----------");
	//						getDepartInfo(value, dept);
	//						// System.out.println("--------------end dept-----------");
	//					}
	//				}
	//			}
	//		}
	//		String param = envolopeField(dept);
	//		dao.updateOrSaveUser(param);
	//
	//		return dept;
	//	}

	//	private String envolopeField(Dept dept) throws IntrospectionException,
	//			InstantiationException, IllegalAccessException,
	//			IllegalArgumentException, InvocationTargetException {
	//		StringBuffer buffer = new StringBuffer();
	//		Class<?> clazz = dept.getClass();
	//		BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
	//		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
	//		buffer.append("<params>");
	//		for (PropertyDescriptor propertyDescriptor : pds) {
	//			String field = propertyDescriptor.getName();
	//			Method readMethod = propertyDescriptor.getReadMethod();
	//			Object o = readMethod.invoke(dept);
	//			field = field.substring(0, 1).toUpperCase() + field.substring(1);
	//
	//			buffer.append("<" + field + "><![CDATA[" + o + "]]></" + field
	//					+ ">");
	//		}
	//		buffer.append("</params>");
	//		return buffer.toString();
	//
	//	}
	//
	//	public void getDept() {
	//
	//	}
	//
	//	public Dept getUserByUidUpdateDeviceNum(String uid) throws NamingException,
	//			SQLException {
	//
	//		LdapDao dao = new LdapDao();
	//		String[] personKeys = { "uid", "cn", "mobile", "mail", "mdevices",
	//				"dept1Code" };
	//
	//		// 注意这里是baseDN的子树
	//		String root = "o=国泰君安证券股份有限公司,dc=orgusers,dc=gtja,dc=net";
	//
	//		// StringBuffer output = new StringBuffer();
	//
	//		SearchControls ctrl = new SearchControls();
	//		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//
	//		String searchFilter = "(uid=" + uid + ")";
	//
	//		NamingEnumeration enu = dc.search(root, searchFilter, ctrl);
	//
	//		Dept dept = null;
	//
	//		while (enu.hasMore()) {
	//			SearchResult sr = (SearchResult) enu.next();
	//			dept = new Dept();
	//
	//			Attributes ab = sr.getAttributes();
	//			for (String string : personKeys) {
	//				Attribute attr = ab.get(string);
	//				String value = null;
	//				if (attr != null) {
	//					value = attr.get().toString();
	//					// 设置人员信息
	//					setLdapValue(string, value, dept);
	//					// 得到人员的部门信息
	//				}
	//			}
	//		}
	//		dao.updateUser(dept);
	//
	//		return dept;
	//	}

	public String getAllAttrsByUid(String uid) throws NamingException {
		String[] attrKeys = { "uid", "cn", "mobile", "mail", "mdevices", "dept1Code" };

		StringBuffer buffer = new StringBuffer();
		buffer.append("<params>");
		DirContext dc = this.getContext();
		// String root = "dc=justsy,dc=com";
		// String searchFilter = "(uid=" + uid + ")";
		String root = "dc=gtja,dc=net";
		String searchFilter = "(uid=*)";
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// 得到搜索结果的枚举
		NamingEnumeration enu = dc.search(root, searchFilter, ctrl);
		while (enu.hasMore()) {
			SearchResult sr = (SearchResult) enu.next();
			Attributes ab = sr.getAttributes();

			for (String string : attrKeys) {
				Attribute attr = ab.get(string);
				String value = attr.get().toString();
				// System.out.println(string + "---->" + value);
				if ("dept1Code".equals(string)) {
					// getDepartInfo(value);
				}
				// System.out.println(map + "--->" + map.size());
			}

			// NamingEnumeration<? extends Attribute> attrs = ab.getAll();
			// while (attrs.hasMore()) {
			// Attribute a = attrs.next();
			//
			// // 获取属性的key值
			// buffer.append("<").append(a.getID()).append(">")
			// .append("<![CDATA[").append(a.get()).append("]]>")
			// .append("</").append(a.getID()).append(">");
			//
			// // System.out.println("key : " + a.getID() + ", value : "
			// // + a.get());
			// }
		}

		if (dc != null) {
			try {
				dc.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		buffer.append("</params>");
		// System.out.println("buffer : " + buffer.toString());
		return buffer.toString();
	}

	/**
	 * 获取部门信息
	 * 
	 * @param deptID
	 *            部门id
	 * @param dept
	 *            部门对象
	 * @return
	 * @throws NamingException
	 */
	//	public Map<String, String> getDepartInfo(String deptID, Dept dept)
	//			throws NamingException {
	//
	//		// logger.debug("deptID : "+deptID+"") ;
	//		// System.out.println("getDepartInfo --> deptID : " + deptID);
	//
	//		String[] attrKeys = { "depID", "cn", "depGrade", "adminID" };
	//		DirContext dc = this.getContext();
	//		Map<String, String> map = new HashMap<String, String>();
	//		String root = "dc=gtja,dc=net";
	//		String searchFilter = "(&(depID=" + deptID + ")(disabled=0))";
	//		SearchControls ctrl = new SearchControls();
	//		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//		// 得到搜索结果的枚举
	//		NamingEnumeration<SearchResult> enu = dc.search(root, searchFilter,
	//				ctrl);
	//		while (enu.hasMore()) {
	//			SearchResult sr = (SearchResult) enu.next();
	//			Attributes ab = sr.getAttributes();
	//			for (String string : attrKeys) {
	//				Attribute attr = ab.get(string);
	//				String value = attr.get().toString();
	//				map.put(string, value);
	//				setLdapUserDept(string, value, dept);
	//			}
	//
	//		}
	//
	//		if (dc != null) {
	//			try {
	//				dc.close();
	//			} catch (NamingException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//
	//		return map;
	//	}
	//
	//	private void setLdapUserDept(String string, String value, Dept dept) {
	//		if ("depID".equals(string)) {
	//			dept.setGroupId(value);
	//		} else if ("cn".equals(string)) {
	//			dept.setGroupName(value);
	//		} else if ("depGrade".equals(string)) {
	//			dept.setGroupLevel(value);
	//		} else if ("adminID".equals(string)) {
	//			dept.setParentGroupID(value);
	//		}
	//
	//	}

	/**
	 * 
	 * 配置连接需要的参数
	 * 
	 * @return
	 */
	private Hashtable<String, String> getEnv() {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		ht.put(Context.PROVIDER_URL, "ldap://10.88.100.173:389");
		// ht.put(Context.PROVIDER_URL,
		// "ldap://10.88.100.173:389/dc=gtja,dc=net");
		ht.put(Context.SECURITY_AUTHENTICATION, "simple");
		ht.put(Context.SECURITY_PRINCIPAL, "cn=**,cn=**");
		ht.put(Context.SECURITY_CREDENTIALS, "**");
		return ht;
	}

	/**
	 * 
	 * 初始化连接
	 * 
	 * @return
	 */
	private DirContext getContext() {
		DirContext dc = null;
		try {
			dc = new InitialDirContext(this.getEnv());
			// System.out.println("Authentication Successful");
		} catch (javax.naming.AuthenticationException ex) {
			ex.printStackTrace();
			// System.out.println("Authentication Failed");
		} catch (Exception x) {
			x.printStackTrace();
			// System.out.println("Error!");
		}
		return dc;
	}

}
