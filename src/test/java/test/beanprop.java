package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cntaiping.tpi.edas.web.validator.Errors;
import com.cntaiping.tpi.edas.web.validator.impl.RequiredValidator;

public class beanprop {

	@Test
	public void test() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("b", "1");
		Map<String,Object> bb = new HashMap<String,Object>();
		bb.put("a", Arrays.asList(map));
		
		RequiredValidator rv = new RequiredValidator();
		rv.init("a.b", null);
		Errors be= new Errors();
		rv.validate(bb, be);
		System.out.println(be.getErrors().size());
	}

}
