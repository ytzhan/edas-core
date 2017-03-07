package com.cntaiping.tpi.edas.action.validator;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Errors {
	 static ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.setSerializationInclusion(Include.NON_NULL); 
	}
	public Errors() {
	}
	public List<FieldError> errors = new ArrayList<FieldError>();

	public List<FieldError> getErrors() {
		return errors;
	}

	public void rejectFieldError(String field, String errMsg) {
		FieldError fe = new FieldError(field, null, errMsg);
		errors.add(fe);
	}
	
	public void rejectError(String errCode, String errMsg) {
		FieldError fe = new FieldError(null, errCode, errMsg);
		errors.add(fe);
	}
	
	public boolean isSucc() {
		return errors.isEmpty();
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
