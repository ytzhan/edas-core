package com.cntaiping.tpi.edas.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.view.AbstractView;

import com.cntaiping.tpi.edas.util.WebUtil;

public class HtmlView extends AbstractView {
	private String viewName;
	private VelocityEngine engine;

	public HtmlView(String viewName, VelocityEngine engine) {
		this.viewName = viewName;
		this.engine = engine;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		VelocityContext vc = new VelocityContext(model);
		Template t = engine.getTemplate(WebUtil.WEB_ROOT+viewName);
		if (t != null) {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			t.merge(vc, response.getWriter());
		}
	}

}
