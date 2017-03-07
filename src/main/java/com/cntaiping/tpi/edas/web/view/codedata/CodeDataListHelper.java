package com.cntaiping.tpi.edas.web.view.codedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeDataListHelper extends CodeDataHelper{
	private List<Map> list=new ArrayList<Map>();
	
	public void add(Object value,String label){
		Map data=new HashMap();
		data.put("value", value);
		data.put("label",label);
		list.add(data);
	}

	@Override
	public Object list() {
		return list;
	}
}
