package com.childe.san.upload ;

public class UnAllowedExtException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UnAllowedExtException() {
	}
	
	public UnAllowedExtException(String msg) {
		super(msg) ;
	}

}
