package com.childe.san.dynamic.interceptor;

public class ServiceImpl implements Service {
//	@Override
	public String greet(String name) {
		String result = "Hello, " + name;
		System.out.println(result);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cyh.proxy.interceptor.test.Service#hello()
	 */
//	@Override
	public String hello() {
		String result = "Hello, world!!" ;
		return result;
	}
}