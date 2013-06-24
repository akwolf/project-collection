package com.childe.san.dynamic.interceptor;

import com.childe.san.dynamic.interceptor.impl.DynamicProxyFactoryImpl;

public class TestDynamicProxy {
	public TestDynamicProxy() {
		DynamicProxyFactory dynamicProxyFactory = new DynamicProxyFactoryImpl();
		Interceptor interceptor = new InterceptorImpl();
		Service service = new ServiceImpl();

		Service proxy = dynamicProxyFactory.createProxy(service, interceptor);
		// Service proxy = DefaultProxyFactory.createProxy(service,
		// interceptor);
		proxy.greet("iwindyforest");
	}

	public static void main(String[] args) {
		new TestDynamicProxy();
	}
}