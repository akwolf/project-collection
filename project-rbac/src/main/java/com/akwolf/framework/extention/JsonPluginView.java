package com.akwolf.framework.extention;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.et.mvc.View;
import com.et.mvc.ViewRendererClass;
import com.et.mvc.util.Json;

/**
 * 对JsonView 的拓展框架中Json类很鸡肋，拓展为json-lib的支持
 */
@ViewRendererClass(JsonPluginViewRenderer.class)
public class JsonPluginView extends View {

	private String json;

	public JsonPluginView() {
		json = "{}";
	}

	public JsonPluginView(JSONObject object) {
		json = object.toString();
	}

	public JsonPluginView(JSONArray array) {
		json = array.toString();
	}

	public JsonPluginView(Map<String, Object> map) {
		json = Json.toJson(map);
	}

	public JsonPluginView(List<?> list) {
		json = Json.toJson(list);
	}

	public JsonPluginView(Object[] objects) {
		json = Json.toJson(objects);
	}

	public JsonPluginView(Object obj) {
		json = Json.toJson(obj);
	}

	/**
	 * 用字符串构造JSON视图
	 * 
	 * @param str
	 *            字符串表示的JSON表达式，如"success:true,age:32,salary:2000.50,name:'名称'"
	 */
	public JsonPluginView(String str) {
		Map<String, Object> map = parseStr(str);
		json = Json.toJson(map);
	}

	@Override
	public String toString() {
		return json;
	}

	private Map<String, Object> parseStr(String str) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String strPart : str.split(",")) {
			String[] ss = strPart.split(":");
			if (ss == null || ss.length != 2) {
				continue;
			}
			String key = ss[0];
			String value = ss[1].trim();
			if (value.startsWith("'") && value.endsWith("'")) {
				map.put(key, value.substring(1, value.length() - 1));
			} else if (value.startsWith("\"") && value.endsWith("\"")) {
				map.put(key, value.substring(1, value.length() - 1));
			} else if (value.equals("true") || value.equals("false")) {
				map.put(key, Boolean.valueOf(value));
			} else if (value.indexOf(".") == -1) {
				try {
					int val = Integer.parseInt(value);
					map.put(key, val);
				} catch (Exception e) {
					map.put(key, value);
				}
			} else {
				try {
					BigDecimal val = new BigDecimal(value);
					map.put(key, val);
				} catch (Exception e) {
					map.put(key, value);
				}
			}
		}
		return map;
	}
}
