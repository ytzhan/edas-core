package com.cntaiping.tpi.edas.action;

import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.web.view.codedata.CodeDataHelper;

public abstract class PageAction{
	protected Object buildPageResult(Object data,int pages){
		HashMap<String,Object> map = new HashMap<String,Object>(2);
		map.put("data", data);
		map.put("pages", pages);
		return map;
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
