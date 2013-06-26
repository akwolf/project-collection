package com.akwolf.rbac;

public class RbacException extends Exception {
	private static final long serialVersionUID = 1L;

	public RbacException(){
		super();
	}
	
	public RbacException(String error){
		super(error);
	}
	
	public RbacException(String error, Throwable t){
		super(error, t);
	}

}
