package com.cntaiping.tpi.edas.action.validator.impl;

import java.text.MessageFormat;

import com.cntaiping.tpi.edas.action.Result;
import com.cntaiping.tpi.edas.action.validator.IValidator;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;

public class MatchValidator implements IValidator {
	private String match;
	@Override
	public void init(Object[] args) {
		if(args.length < 1){
			throw new BaseRuntimeException("MatchValidator params:[pattern]");
		}else{
			this.match = (String) args[0];
		}
	}

	@Override
	public void validate(String route, Object data, Result error) {
		String value = (String) data;
		if(!value.matches(match)){
			error.rejectFieldError(route, MessageFormat.format("{0}与模式【{1}】不匹配", value, match));
		}
	}
}
