package com.childe.san.dynamic.interceptor.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.childe.san.dynamic.interceptor.DynamicProxyFactory;
import com.childe.san.dynamic.interceptor.Interceptor;

public class DynamicProxyFactoryImpl implements DynamicProxyFactory {
	/**
	 * 生成动态代理,并且在调用代理执行函数的时候使用拦截器
	 * 
	 * @param target
	 *            需要代理的实例
	 * @param interceptor
	 *            拦截器实现,就是我们希望代理类执行函数的前后, 抛出异常,finally的时候去做写什么
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T createProxy(T target, Interceptor interceptor) {
		// 当前对象的类加载器
		ClassLoader classLoader = target.getClass().getClassLoader();
		// 获取此对象实现的所有接口
		Class<?>[] interfaces = target.getClass().getInterfaces();
		// 利用DynamicProxyInvocationHandler类来实现InvocationHandler
		InvocationHandler handler = new DynamicProxyInvocationHandler(target, interceptor);

		//		System.out.println(Proxy.newProxyInstance(classLoader, interfaces, handler).getClass().isInstance(target));
		//		System.out.println(target.getClass().isInstance(Proxy.newProxyInstance(classLoader, interfaces, handler)));

		return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
	}
}