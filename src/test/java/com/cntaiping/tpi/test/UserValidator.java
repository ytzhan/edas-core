package com.cntaiping.tpi.test;

import java.util.Map;

import com.cntaiping.tpi.edas.action.EntityValidator;
import com.cntaiping.tpi.edas.action.Result;

public class UserValidator extends EntityValidator<Map<?,?>>{

	@Override
	public void config() {
		register("name", "Required");
		register("age", "NumberRange",18,45);
		register("phone", "Match","1[3578][0-9]{9}");
		register("idCard","StringLength",18,18);
		register("policy.no","Required");
	}
	@Override
	public void customValid(Map<?, ?> target, Result errors) {
		errors.rejectError("S001", "自定义校验错误");
	}

}
