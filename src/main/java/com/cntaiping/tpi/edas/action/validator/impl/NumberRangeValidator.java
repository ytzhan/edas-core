package com.cntaiping.tpi.edas.action.validator.impl;

import java.math.BigDecimal;

import com.cntaiping.tpi.edas.action.validator.Errors;
import com.cntaiping.tpi.edas.action.validator.IValidator;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;

public class NumberRangeValidator implements IValidator {
	private BigDecimal min;
	private BigDecimal max;

	@Override
	public void init(Object[] args) {
		if (args.length < 2) {
			throw new BaseRuntimeException("NumberRangeValidator params:<min>,<max>");
		}
		this.min = new BigDecimal(String.valueOf(args[0]));
		this.max = new BigDecimal(String.valueOf(args[1]));
	}

	@Override
	public void validate(String route, Object data, Errors error) {
		BigDecimal value = new BigDecimal(String.valueOf(data));
		if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
			error.rejectValue(route, value + " 不在区间【" + min + "-" + max + "]内");
		}
	}

}
