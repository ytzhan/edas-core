package com.cntaiping.tpi.edas.web.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import com.cntaiping.tpi.edas.util.WebUtil;

public class CustomViewResolver extends AbstractTemplateViewResolver {
	
	public CustomViewResolver() {
		setViewClass(CustomView.class);
	}
	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		if (WebUtil.PAGE_VIEW.equals(viewName)) {
			return super.resolveViewName(viewName, locale);
		}
		return null;
	}
	
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
