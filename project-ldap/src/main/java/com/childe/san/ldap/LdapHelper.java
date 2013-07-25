package com.childe.san.ldap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

/**
 * 参考
 * http://www.novell.com/documentation/developer/samplecode/jldap_sample/
 * 
 * http://www.micmiu.com/opensource/java-ldap-demo/
 * 
 * @author zhangh
 * @createTime 2013-7-22 上午11:03:08
 */
// TODO:对查询组进行排序
//
public class LdapHelper {

	private static final String PROVIDER_URL = "10.88.100.173";
	private static final int PROVIDER_PORT = 389;
	private static final String SECURITY_PRINCIPAL = "cn=gtjaldap,cn=ibmpolicies";
	private static final String SECURITY_CREDENTIALS = "****";

	/** 组需要从ldap中获取的属性 */
	private static final String[] GROUP_ATTRS = { "depID", "xPath", "cn", "depGrade", "adminID" };
	/** 人员需要获取的属性 */
	private static final String[] PERSON_ATTRS = { "uid", "cn", "mobile", "mail", "mdevices", "dept1Code" };

	/** 与线程相关的ldap连接 */
	private LDAPConnection ldapConnection;

	public LdapHelper() {
		ldapConnection = new LDAPConnection();
		init();
	}

	/**
	 * 对连接进行初始化
	 */
	private void init() {
		try {
			ldapConnection.connect(PROVIDER_URL, PROVIDER_PORT);
			ldapConnection.bind(LDAPConnection.LDAP_V3, SECURITY_PRINCIPAL, SECURITY_CREDENTIALS.getBytes());
		} catch (LDAPException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有部门
	 * 
	 * @return
	 */
	public List<Group> findAllGroups() {
		String filter = "(&(objectClass=GtjaOrganization)(disabled=0))";
		return findGroups(filter);
	}

	/**
	 * 通过id查询Group
	 * 
	 * @param departId
	 * @return
	 */
	public Group findGroupById(String departId) {
		String searchFilter = "(&(depID=" + departId + ")(disabled=0))";
		List<Group> list = findGroups(searchFilter);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * 指定查询条件查询groups
	 * 
	 * @param filter
	 * @return
	 */
	public List<Group> findGroups(String filter) {
		List<Group> list = null;
		try {
			LDAPSearchResults lr = search(filter, GROUP_ATTRS);
			list = handleAttrs(lr, Group.class);
		} catch (LDAPException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通用查询
	 * 
	 * @param filter
	 * @param attrs
	 * @return
	 * @throws LDAPException
	 */
	public LDAPSearchResults search(String filter, String[] attrs) throws LDAPException {
		String base = "o=国泰君安证券股份有限公司,dc=orgusers,dc=gtja,dc=net";
		LDAPSearchResults lr = ldapConnection.search(base, LDAPConnection.SCOPE_SUB, filter, attrs, false);
		return lr;
	}

	//--------------
	//-- Person ----
	//--------------
	/**
	 * 
	 * 通过uid查询person
	 * @param uid
	 * @return
	 */
	public Person findPersonByUid(String uid) {
		String searchFilter = "(uid=" + uid + ")";
		List<Person> list = findPersons(searchFilter);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询部门下的所有人员
	 * 
	 * @param departId
	 * @return
	 */
	public List<Person> findPersonsByDepartId(String departId) {
		String filter = "(&(dept1Code=" + departId + ")(objectClass=GtjaPerson))";
		return findPersons(filter);
	}

	public List<Person> findPersons(String filter) {
		List<Person> list = null;
		try {
			LDAPSearchResults lr = search(filter, PERSON_ATTRS);
			list = handleAttrs(lr, Person.class);
		} catch (LDAPException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 断开连接
	 */
	public void free() {
		try {
			if (this.ldapConnection != null)
				this.ldapConnection.disconnect();
		} catch (LDAPException e) {
		}
	}

	public <T> List<T> handleAttrs(LDAPSearchResults lr, Class<T> clazz) throws LDAPException {

		List<T> list = new ArrayList<T>();

		Field[] fields = clazz.getDeclaredFields();
		// attrName与Field的映射
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field field : fields) {
			field.setAccessible(true);
			Annotation[] annos = field.getAnnotations();
			if (annos.length < 1) {
				map.put(field.getName(), field);
				continue;
			}
			AttrName anno = (AttrName) annos[0];
			map.put(anno.value(), field);
		}

		while (lr.hasMore()) {
			T instance = null;
			try {
				instance = clazz.newInstance();
				LDAPEntry entry = lr.next();
				LDAPAttributeSet attributeSet = entry.getAttributeSet();
				for (Object object : attributeSet) {
					LDAPAttribute attribute = (LDAPAttribute) object;
					String attrName = attribute.getName();
					String attrValue = attribute.getStringValue();
					Field f = map.get(attrName);
					BeanUtils.setProperty(instance, f.getName(), attrValue);
				}
				list.add(instance);
			} catch (InstantiationException e) {
				System.err.println("泛型类初始化异常，是否有默认构造方法");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.err.println("泛型类初始化异常，构造方法是否为public");
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
