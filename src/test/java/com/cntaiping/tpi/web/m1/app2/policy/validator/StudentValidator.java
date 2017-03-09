package com.cntaiping.tpi.web.m1.app2.policy.validator;

import com.cntaiping.tpi.edas.action.EntityValidator;
import com.cntaiping.tpi.edas.action.Result;
import com.cntaiping.tpi.web.m1.app2.policy.dto.Student;

public class StudentValidator extends EntityValidator<Student>{

	@Override
	public void config() {
		register("name", "Required");
		register("startDate", "DateRange", "2017-01-01","2018-01-01");
		register("email", "Match","[a-zA-z0-9_.]+@[a-zA-z0-9_.]");
	}
	@Override
	public void customValid(Student target, Result errors) {
		if("null".equals(target.getName())){
			errors.rejectError("U0001", "姓名为非法字符");
		}
	}

}
