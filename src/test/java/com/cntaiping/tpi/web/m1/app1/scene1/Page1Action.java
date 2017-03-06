package com.cntaiping.tpi.web.m1.app1.scene1;

import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.NullClass;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.edas.demo.dto.UserDto;


@Action(value="page1",entity=UserDto.class)
public class Page1Action extends PageAction{
	@EntityEvent(name="onCmdClick")
	public Object action1(UserDto entity){
		Map result=new HashMap();
		result.put("abc", "data2");
		return result;
	}

	@EntityFunction
	public Object createEntity() {
		Map result=new HashMap();
		result.put("abc", "data1");
		return result;
	}
	
	@RemoteFunction(name="rpc",param=NullClass.class)
	public Object remoteCall(){
		Map result=new HashMap();
		result.put("abc", "data2");
		return result;
	}
	
}
