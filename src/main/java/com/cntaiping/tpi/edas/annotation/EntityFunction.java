package com.cntaiping.tpi.edas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityFunction {
	public String name() default "";
	public Class<?> param() default NullClass.class;//NullClass为带默认值的空实体对象
}
