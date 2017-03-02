package com.cntaiping.tpi.edas.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.web.servlet.view.AbstractView;

import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsView extends AbstractView {
	private String viewName;
	private VelocityEngine engine;
	public static final String DEFAULT_PAGE_JS = "/com/cntaiping/tpi/edas/web/view/default_page.js";

	public JsView(String viewName, VelocityEngine engine) {
		this.viewName = viewName;
		this.engine = engine;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		VelocityContext context = new VelocityContext();
		PageAction action = (PageAction) model.get(WebUtil.PAGE_ACTION_CLASS);
		//context.put("page", model.get(WebUtil.PAGE_ACTION));
		//context.put("functions", action.getEntityFunctions());
		//ObjectMapper objectMapper = new ObjectMapper();
		//context.put("data", objectMapper.writeValueAsString(action.createDefault()));
		context.put("_pageObject", action);
		Template t = null;
		try {
			t = engine.getTemplate(WebUtil.WEB_ROOT+viewName);
		} catch (ResourceNotFoundException e) {
			t = engine.getTemplate(DEFAULT_PAGE_JS);
		}
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		t.merge(context, response.getWriter());

	}

}
