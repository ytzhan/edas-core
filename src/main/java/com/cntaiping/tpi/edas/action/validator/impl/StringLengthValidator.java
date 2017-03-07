package com.cntaiping.tpi.edas.action.validator.impl;

import java.text.MessageFormat;

import com.cntaiping.tpi.edas.action.Result;
import com.cntaiping.tpi.edas.action.validator.IValidator;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;

public class StringLengthValidator implements IValidator {
	private int minLen = 0;
	private int maxLen;

	@Override
	public void init(Object[] args) {
		if (args.length < 1) {
			throw new BaseRuntimeException("StringLengthValidator params:[minLen],<maxLen>");
		} else if (args.length == 1) {
			maxLen = Integer.parseInt(String.valueOf(args[0]));
		} else {
			minLen = Integer.parseInt(String.valueOf(args[0]));
			maxLen = Integer.parseInt(String.valueOf(args[1]));
		}

	}

	@Override
	public void validate(String route, Object data, Result error) {
		String value = (String) data;
		int len = 0;
		if (value != null) {
			len = value.length();
		}
		if (len < minLen || len > maxLen) {
			error.rejectFieldError(route, MessageFormat.format("长度{0}不在区间内【{1}-{2}】", len, minLen, maxLen));
		}
	}

}
