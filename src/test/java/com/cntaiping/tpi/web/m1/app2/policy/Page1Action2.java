package com.cntaiping.tpi.web.m1.app2.policy;

import java.text.SimpleDateFormat;
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


@Action(page="index",entity=UserDto.class)
public class Page1Action2 extends PageAction{
	@RemoteFunction(name = "firstPage", param = NullClass.class)
	public Object firstPage(){
		ArrayList<Object> result=new ArrayList<Object>();
		for(int i = 1;i<=8;i++){
			HashMap<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("a", i);
			paramMap.put("url", "/pic/tp.png");
			paramMap.put("title", "维修任务"+i);
			paramMap.put("content", "太平财险复业15周年司庆洪荒之礼人性袭来，12月20日零点开始，你准备好了吗？");
			paramMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			result.add(paramMap);
		}
		
		return this.buildPageResult(result, 10);
	}
	
	
	@RemoteFunction(name = "nextPage", param = Integer.class)
	public Object nextPage(int page){
		ArrayList<Object> result=new ArrayList<Object>();
		for(int i = (page-1)*8+1;i<=8*page;i++){
			HashMap<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("url", "/pic/tp.png");
			paramMap.put("title", "维修任务"+i);
			paramMap.put("content", "车辆信息。。。出险信息。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
			paramMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			result.add(paramMap);
		}
		return result;
	}
	
}
