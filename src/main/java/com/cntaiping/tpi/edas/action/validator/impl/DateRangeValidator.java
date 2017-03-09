package com.cntaiping.tpi.edas.action.validator.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cntaiping.tpi.edas.action.Result;
import com.cntaiping.tpi.edas.action.validator.IValidator;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;

public class DateRangeValidator implements IValidator {
	private Date min;
	private Date max;

	private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		};
	};

	@Override
	public void init(Object[] args) {
		if (args.length < 2) {
			throw new BaseRuntimeException("DateRangeValidator params:<min>,<max>");
		}

		this.min = parse(args[0]);
		this.max = parse(args[1]);
	}

	private Date parse(Object param) {
		if (param instanceof Date) {
			return (Date) param;
		} else if (param instanceof String) {
			try {
				return sdf.get().parse((String) param);
			} catch (ParseException e) {
				throw new BaseRuntimeException("param format error:" + param, e);
			}
		} else {
			throw new BaseRuntimeException("param format error:" + param);
		}
	}

	@Override
	public void validate(String route, Object data, Result error) {
		Date value = (Date) data;
		if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
			error.rejectFieldError(route, value + " 不在区间【" + sdf.get().format(min) + "-" + sdf.get().format(max) + "]内");
		}
	}

}
