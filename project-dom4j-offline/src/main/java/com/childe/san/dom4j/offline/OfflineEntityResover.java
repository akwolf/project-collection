package com.childe.san.dom4j.offline;

import java.io.IOException;
import java.io.Serializable;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 在不能连接外网情况下对指定的文档实体进行解析
 * 
 * 文件名 : OfflineEntityResover.java
 * 
 * @version : 1.0
 * @author : zhangh
 * @email : zhangh@justsy.com
 * @create date : 2012-11-22
 */
public class OfflineEntityResover implements EntityResolver, Serializable {
	private static final long serialVersionUID = 1L;
	protected String uriPrefix;

	public OfflineEntityResover(String systemId) {
		this.uriPrefix = getUriPrefix(systemId);
	}

	protected String getUriPrefix(String systemId) {
		String prefix = null;
		if ((systemId != null) && (systemId.length() > 0)) {
			int idx = systemId.lastIndexOf('/');
			if (idx > 0) {
				prefix = systemId.substring(0, idx + 1);
			}
		}
		return prefix;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		if ((systemId != null) && (systemId.length() > 0)) {
			if ((uriPrefix != null) && (systemId.indexOf(':') <= 0)) {
				systemId = uriPrefix + systemId;
			}
		}
		if (systemId.equals("http://www.apple.com/DTDs/PropertyList-1.0.dtd")) {
			return new InputSource(OfflineEntityResover.class.getClassLoader().getResourceAsStream(
					"dtd/PropertyList-1.0.dtd"));
		} else {
			return new InputSource(systemId);
		}
	}

}
