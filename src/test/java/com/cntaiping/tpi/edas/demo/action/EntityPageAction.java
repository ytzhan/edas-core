package com.cntaiping.tpi.edas.demo.action;

import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.action.intf.IDefaultEntity;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityFunction;


@Action(module="m1",app="app1",page="page1")
public class EntityPageAction extends PageAction implements IDefaultEntity{
	@EntityFunction(name="onCmdClick")
	public Object action1(Object entity){
		Map result=new HashMap();
		result.put("abc", "data2");
		return result;
	}

	@Override
	public Object createEntity() {
		Map result=new HashMap();
		result.put("abc", "data1");
		return result;
	}
	
}
