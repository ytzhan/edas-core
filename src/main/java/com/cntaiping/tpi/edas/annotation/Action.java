package com.cntaiping.tpi.edas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

import com.cntaiping.tpi.edas.action.EmptyEntityValidator;
import com.cntaiping.tpi.edas.action.EntityValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Action {
	public String value();
	public Class<?> entity();
	public Class<? extends EntityValidator> validator() default EmptyEntityValidator.class;
}
