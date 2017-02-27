package com.cntaiping.tpi.edas.web.view;

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomView extends AbstractTemplateView {

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		VelocityContext context = new VelocityContext();
		PageAction action = (PageAction) model.get(WebUtil.PAGE_ACTION);
		context.put("functions", action.getEntityFunctions());
		ObjectMapper objectMapper = new ObjectMapper();
		context.put("data", objectMapper.writeValueAsString(action.createDefault()));
		Template t = engine.getTemplate("/com/cntaiping/tpi/edas/web/view/Page.vm");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		t.merge(context, response.getWriter());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		engine = new VelocityEngine();
		Properties p = new Properties();
		p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		engine.init(p);
	}

	VelocityEngine engine;

}
