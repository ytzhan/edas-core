package com.cntaiping.tpi.edas;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cntaiping.tpi.edas","com.cntaiping.tpi.web","com.cntaiping.tpi.app"})
public class StartApp extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(StartApp.class);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(StartApp.class).run(args);
	}
}
