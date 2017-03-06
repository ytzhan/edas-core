package com.cntaiping.tpi.edas.action.validator;

import java.util.ArrayList;
import java.util.List;

public class Errors {
	public List<FieldError> errors = new ArrayList<FieldError>();
	public List<FieldError> getErrors() {
		return errors;
	}
	public void rejectValue(String field, String errMsg){
		FieldError fe = new FieldError(field, null, errMsg);
		errors.add(fe);
	}
}
