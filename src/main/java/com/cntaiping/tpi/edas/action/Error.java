package com.cntaiping.tpi.edas.action;

public class Error {
	private String field;
	private String errCode;
	private String errMsg;
	public Error(String field,String errCode,String errMsg) {
		this.field = field;
		this.errMsg = errMsg;
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public String getField() {
		return field;
	}
}
