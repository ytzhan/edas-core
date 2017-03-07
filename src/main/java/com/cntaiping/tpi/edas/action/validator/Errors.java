package com.cntaiping.tpi.edas.action.validator;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Errors {
	public List<FieldError> errors = new ArrayList<FieldError>();

	public List<FieldError> getErrors() {
		return errors;
	}

	public void rejectValue(String field, String errMsg) {
		FieldError fe = new FieldError(field, null, errMsg);
		errors.add(fe);
	}

	public boolean isSucc() {
		return errors.isEmpty();
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
