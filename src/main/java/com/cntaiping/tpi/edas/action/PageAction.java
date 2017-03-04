package com.cntaiping.tpi.edas.action;

import java.util.HashMap;

public abstract class PageAction{
	protected Object buildPageResult(Object data,int pageCount){
		HashMap result=new HashMap();
		result.put("data", data);
		result.put("count", pageCount);
		return result;
	}
}
