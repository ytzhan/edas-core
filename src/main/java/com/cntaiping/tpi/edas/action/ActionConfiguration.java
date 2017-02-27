package com.cntaiping.tpi.edas.action;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AnnotationActionDispatcher.class})
@Configuration
public class ActionConfiguration {

	public ActionConfiguration() {
		System.out.println("ActionConfiguration  running start .....");
	}
	
}
