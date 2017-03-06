package com.cntaiping.tpi.edas.action.validator;

public interface IValidator {
	public void init(String path, Object[] args);

	public void validate(Object data, Errors error);
}