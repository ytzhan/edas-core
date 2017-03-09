package com.cntaiping.tpi.edas.action;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Result {
	static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public Result() {
	}

	private List<Error> errors = new ArrayList<Error>();

	private STATUS status = STATUS.SUCC;

	public STATUS getStatus() {
		return status;
	}
	public Result setStatus(STATUS status) {
		this.status = status;
		return this;
	}

	public Object getData() {
		return data;
	}

	private Object data;
	
	

	public List<Error> getErrors() {
		return errors;
	}

	public Result rejectFieldError(String field, String errMsg) {
		Error fe = new Error(field, null, errMsg);
		errors.add(fe);
		return this;
	}

	public Result rejectError(String errCode, String errMsg) {
		Error fe = new Error(null, errCode, errMsg);
		errors.add(fe);
		return this;
	}

	public Result attachData(Object data) {
		if(data instanceof Result){
			Result src = (Result) data;
			this.data = src.data;
			this.status = src.status;
			this.errors.addAll(src.errors);
		}else{
			this.data = data;
		}
		return this;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	enum STATUS {
		SUCC, SYS_ERROR, VALID_ERROR, USER_ERROR
	}
}
