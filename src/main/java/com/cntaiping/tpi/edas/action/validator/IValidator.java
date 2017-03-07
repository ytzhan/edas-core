package com.cntaiping.tpi.edas.action.validator;

import com.cntaiping.tpi.edas.action.Result;

public interface IValidator {
	public void init(Object[] args);

	public void validate(String route, Object data, Result error);
}