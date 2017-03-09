package com.cntaiping.tpi.web.m1.app2.policy;

import java.util.Date;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.web.m1.app2.policy.dto.Student;
import com.cntaiping.tpi.web.m1.app2.policy.validator.StudentValidator;


@Action(page="detail",entity=Student.class,validator=StudentValidator.class)
public class DetailAction extends PageAction{
	@RemoteFunction(name="find",param=String.class)
	public Student defaultEntity(String id){
		Student student=new Student();
		student.setName(id);
		student.setField1("ghghghhjhjhj");
		student.setEmail("@cntaiping.com");
		return student;
	}
	
	@EntityEvent(name="onSureClick")
	public Student save(Student entity){
		entity.setName(entity.getName()+" save12223...."+new Date());
		return entity;
	}
}
