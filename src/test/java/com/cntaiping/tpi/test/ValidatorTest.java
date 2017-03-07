package com.cntaiping.tpi.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.action.validator.Errors;
import com.cntaiping.tpi.edas.action.validator.ValidatorFactory;

public class ValidatorTest {
	public static void main(String[] args) throws Exception {
		ValidatorFactory vf = new ValidatorFactory();
		vf.afterPropertiesSet();
		Map<String,Object> user = new HashMap<String,Object>();
		user.put("name", "vidy");
		user.put("age", 18);
		user.put("phone", "13412345678");
		user.put("idCard", "123456789012345678");
		
		Map<String,Object> policy = new HashMap<String,Object>();
		policy.put("no", "111111111111111");
		user.put("policy", Arrays.asList(policy));
		
		TestValidator tv = new TestValidator();
		tv.init(vf);
		Errors  error = tv.valid(user);
		System.out.println(error);
	}
}
