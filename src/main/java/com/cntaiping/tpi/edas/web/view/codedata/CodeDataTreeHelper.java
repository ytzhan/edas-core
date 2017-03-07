package com.cntaiping.tpi.edas.web.view.codedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeDataTreeHelper extends CodeDataHelper{
	private Map<String,List> maps=new HashMap<String,List>();
	
	public void add(String set,Object value,String label){
		List list=maps.get(set);
		if (list==null){
			list=new ArrayList();
			maps.put(set,list);
		}
		Map data=new HashMap();
		data.put("value", value);
		data.put("label",label);
		list.add(data);
	}

	@Override
	public Object list() {
		return maps;
	}
}
