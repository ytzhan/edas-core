package com.cntaiping.tpi.edas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface RPC {

	Class<?>[] interfaceClass() default void.class;
	
	String version() default "1.0";
	
}
