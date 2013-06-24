package com.childe.san.dynamic.interceptor.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.childe.san.dynamic.interceptor.Interceptor;

/**
 * 动态代理的调用处理器
 * 
 * @author chen.yinghua
 */
public class DynamicProxyInvocationHandler implements InvocationHandler {
	private Object target;
	private Interceptor interceptor;

	/**
	 * @param target
	 *            需要代理的实例
	 * @param interceptor
	 *            拦截器
	 */
	public DynamicProxyInvocationHandler(Object target, Interceptor interceptor) {
		this.target = target;
		this.interceptor = interceptor;
	}

	/**
	 * @param proxy
	 *            所生成的代理对象
	 * @param method
	 *            调用的方法示例
	 * @args args 参数数组
	 * @Override
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;

		try {
			// 在执行method之前调用interceptor去做什么事
			this.interceptor.before(method, args);
			// 在这里我们调用原始实例的method
			result = method.invoke(this.target, args);
			// 在执行method之后调用interceptor去做什么事
			this.interceptor.after(method, args);
		} catch (Throwable throwable) {
			// 在发生异常之后调用interceptor去做什么事
			this.interceptor.afterThrowing(method, args, throwable);
			throw throwable;
		} finally {
			// 在finally之后调用interceptor去做什么事
			interceptor.afterFinally(method, args);
		}

		return result;
	}

}