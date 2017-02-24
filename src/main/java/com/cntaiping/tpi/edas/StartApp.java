package com.cntaiping.tpi.edas;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
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
