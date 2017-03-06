package com.cntaiping.tpi.edas.web.validator;

public class FieldError {
	private String field;
	private String errCode;
	private String errMsg;
	public FieldError(String field,String errCode,String errMsg) {
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
