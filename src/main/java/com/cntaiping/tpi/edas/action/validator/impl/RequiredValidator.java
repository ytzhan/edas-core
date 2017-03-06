package com.cntaiping.tpi.edas.action.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.cntaiping.tpi.edas.action.validator.BaseValidator;
import com.cntaiping.tpi.edas.action.validator.Errors;

public class RequiredValidator extends BaseValidator {

	@Override
	protected void validateMetaData(String route, Object data, Errors error) {
		if (data instanceof String && StringUtils.isBlank((String) data)) {
			error.rejectValue(route, "字段为空");
		} else if (data == null) {
			error.rejectValue(route, "字段为空");
		}
	}



}
