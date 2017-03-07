package com.cntaiping.tpi.test;

import com.cntaiping.tpi.edas.action.EntityValidator;

public class TestValidator extends EntityValidator{

	@Override
	public void config() {
		register("name", "Required");
		register("age", "NumberRange",18,45);
		register("phone", "Match","1[3578][0-9]{9}");
		register("idCard","StringLength",18,18);
		register("policy.no","Required");
	}

}
