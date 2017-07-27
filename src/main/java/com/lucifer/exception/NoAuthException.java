package com.lucifer.exception;

public class NoAuthException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAuthException(){
		super();
	}
	
	public NoAuthException(String msg){
		super(msg);
	}

}
