package com.childe.san.dynamic.interceptor;

public interface DynamicProxyFactory {
	/**
	 * 生成动态代理,并且在调用代理执行函数的时候使用拦截器
	 * 
	 * @param clazz
	 *            需要实现的接口
	 * @param target
	 *            实现此接口的类
	 * @param interceptor
	 *            拦截器 * @return
	 */
	public <T> T createProxy(T target, Interceptor interceptor);
}