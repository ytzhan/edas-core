package com.cntaiping.tpi.edas.web;

public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public BaseRuntimeException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BaseRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
		this.msg = msg;
	}

	public BaseRuntimeException(Throwable cause) {
		super(cause);
		this.msg = cause.getMessage();
	}
}
