package com.cntaiping.tpi.web.m1.app2.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.NullClass;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.edas.demo.dto.UserDto;


@Action(value="index",entity=UserDto.class)
public class DetailAction extends PageAction{

	@RemoteFunction(name = "firstPage", param = NullClass.class)
	public Object firstPage(){
		int i=1;
		ArrayList result=new ArrayList();
		HashMap paramMap=new HashMap();
		paramMap.put("a",i*100+23);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+32);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+33);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+34);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+35);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+36);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+37);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+38);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+39);
		result.add(paramMap);

		HashMap resultMap=new HashMap();
		
		return this.buildPageResult(result, 10);
	}
	
	
	@RemoteFunction(name = "nextPage", param = Integer.class)
	public Object nextPage(int page){
		int i=page;
		ArrayList result=new ArrayList();
		HashMap paramMap=new HashMap();
		paramMap.put("a",i*100+23);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+32);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+33);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+34);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+35);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+36);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+37);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+38);
		result.add(paramMap);
		paramMap=new HashMap();
		paramMap.put("a",i*100+39);
		result.add(paramMap);
		return result;
	}
}
