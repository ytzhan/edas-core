package com.cntaiping.tpi.edas.action;

import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.web.view.codedata.CodeDataHelper;

public abstract class PageAction{
	protected Object buildPageResult(Object data,int pageCount){
		HashMap result=new HashMap();
		result.put("data", data);
		result.put("count", pageCount);
		return result;
	}
	
	private Map<String,CodeDataHelper> codeDataHelpers=new HashMap<String,CodeDataHelper>();
	protected void addCodeData(String name,CodeDataHelper helper){
		codeDataHelpers.put(name, helper);
	}
	
	public CodeDataHelper getCodeDataHelper(String name){
		return codeDataHelpers.get(name);
	}
	
	public String[] getCodeDataHelper(){
		return codeDataHelpers.keySet().toArray(new String[0]);
	}
}
