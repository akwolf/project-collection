package com.akwolf.rbac.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyUtil {

	/**
	 * 调用一个类的方法并返回这个类的实例
	 * 
	 * @param <T>
	 * @param clazz
	 * @param method
	 * @param args
	 * @return
	 */
	public static <T> T setProperty(Class<T> clazz, String method,
			Object... args) {

		Method[] methods = clazz.getMethods();
		T instance = null;
		try {
			instance = clazz.newInstance();

			for (Method m : methods) {
				if (m.getName().equals(method)) {
					m.invoke(instance, args);
				}
			}
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return instance;
	}

	/**
	 * 调用一个实例的方法并返回这个实例
	 * 
	 * @param <T>
	 * @param instance
	 * @param method
	 * @param args
	 * @return
	 */
	public static <T> T invokeMethod(T instance, String method, Object... args) {
		Method[] methods = instance.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				try {
					m.invoke(instance, args);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}
	
	
}
