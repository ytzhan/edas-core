package com.cntaiping.tpi.web.m1.app3.endorse;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.NullClass;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.web.m1.app2.policy.dto.Student;

@Action(page="first",entity=Student.class)
public class FirstPage extends PageAction {
	@RemoteFunction(name="find",param=NullClass.class)
	public Object find(){
		Student studeng=new Student();
		studeng.setField1("张三");
		return studeng;
	}
	@EntityEvent(name="onSureClick")
	public Student save(Student s){
		s.setField1("zhangsan");
		return s;
	}
}
