package com.cntaiping.tpi.web.m1.app3.policy;

import java.util.Date;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.edas.web.view.codedata.CodeDataListHelper;
import com.cntaiping.tpi.edas.web.view.codedata.CodeDataTreeHelper;
import com.cntaiping.tpi.web.m1.app2.policy.dto.Student;


@Action(page="detail",entity=Student.class)
public class Detail3Action extends PageAction{
	@RemoteFunction(name="find",param=String.class)
	public Student defaultEntity(String id){
		Student student=new Student();
		student.setName(id);
		student.setRadio("apple");
		student.setEmail("@cntaiping.com");
		return student;
	}
	
	@EntityEvent(name="onSureClick")
	public Student save(Student entity){
		entity.setName(entity.getName()+" save...."+new Date());
		return entity;
	}
	
	public Detail3Action(){
		CodeDataListHelper helper=new CodeDataListHelper();
		helper.add("SZ","深圳");
		helper.add("ZH","珠海");
		addCodeData("city", helper);
		
		CodeDataTreeHelper thelper=new CodeDataTreeHelper();
		thelper.add("SZ","LH", "罗湖");
		thelper.add("SZ","FT", "福田");
		thelper.add("SZ","NS", "南山");
		
		
		thelper.add("ZH","JW", "金湾");
		thelper.add("ZH","XZ", "香山");
		addCodeData("citys", thelper);
	}
}
