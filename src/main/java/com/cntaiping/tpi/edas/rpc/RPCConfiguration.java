package com.cntaiping.tpi.edas.rpc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RPCRefRegProcessor.class,RPCServRegProcessor.class})
@Configuration
public class RPCConfiguration {

	public RPCConfiguration() {
		System.out.println("RPCConfiguration  running start .....");
	}
	
}
