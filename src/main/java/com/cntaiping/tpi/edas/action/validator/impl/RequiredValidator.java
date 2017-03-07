package com.cntaiping.tpi.edas.action.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.cntaiping.tpi.edas.action.Result;
import com.cntaiping.tpi.edas.action.validator.IValidator;

public class RequiredValidator implements IValidator {

	@Override
	public void init(Object[] args) {

	}

	@Override
	public void validate(String route, Object data, Result error) {
		if (data instanceof String && StringUtils.isBlank((String) data)) {
			error.rejectFieldError(route, "字段为空");
		} else if (data == null) {
			error.rejectFieldError(route, "字段为空");
		}
	}
}
