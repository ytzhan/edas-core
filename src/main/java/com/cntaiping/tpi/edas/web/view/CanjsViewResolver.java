package com.cntaiping.tpi.edas.web.view;

import java.util.Locale;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class CanjsViewResolver implements ViewResolver, Ordered, InitializingBean {
	public CanjsViewResolver() {
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (viewName.endsWith(".stache")) {
			return new StacheView(viewName, engine);
		} else if (viewName.endsWith(".js")) {
			return new JsView(viewName, engine);
		} else if (viewName.endsWith(".html")) {
			return new HtmlView(viewName, engine);
		}
		return null;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

	VelocityEngine engine;

	@Override
	public void afterPropertiesSet() throws Exception {
		engine = new VelocityEngine();
		Properties p = new Properties();
		p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		p.put("input.encoding", "UTF-8");
		p.put("output.encoding", "UTF-8");
		engine.loadDirective("com.cntaiping.tpi.edas.web.view.velocity.directive.Entity");
		engine.loadDirective("com.cntaiping.tpi.edas.web.view.velocity.directive.DefaultEntity");
		engine.loadDirective("com.cntaiping.tpi.edas.web.view.velocity.directive.UpateEvents");
		engine.loadDirective("com.cntaiping.tpi.edas.web.view.velocity.directive.JSON");
		engine.init(p);
	}

}
