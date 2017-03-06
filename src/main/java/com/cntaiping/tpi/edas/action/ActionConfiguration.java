package com.cntaiping.tpi.edas.action;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cntaiping.tpi.edas.action.validator.ValidatorFactory;

@Import({ AnnotationActionDispatcher.class })
@Configuration
public class ActionConfiguration {

	public ActionConfiguration() {
		System.out.println("ActionConfiguration  running start .....");
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return  new ValidatorFactory();
	}
}
