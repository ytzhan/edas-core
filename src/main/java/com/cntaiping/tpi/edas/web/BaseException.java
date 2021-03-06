package com.cntaiping.tpi.edas.web;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public BaseException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
		this.msg = msg;
	}

	public BaseException(Throwable cause) {
		super(cause);
		this.msg = cause.getMessage();
	}
}
