package com.cntaiping.tpi.edas.action.validator;

public interface IValidator {
	public void init(Object[] args);

	public void validate(String route, Object data, Errors error);
}