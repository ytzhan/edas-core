package com.cntaiping.tpi.edas.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.cntaiping.tpi.edas.action.IActionDispatcher;
import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.util.WebUtil;

@Controller
@RequestMapping(value = "/{module}/{app}/{page}")
public class PageControler implements ApplicationContextAware {
	private String PATH_TEMPLATE = "classpath:/stache/{0}/{1}/{2}/index.stache";

	@RequestMapping(value = "/index.js", method = RequestMethod.GET)
	public ModelAndView indexJs(@PathVariable String module, @PathVariable String app, @PathVariable String page)
			throws IOException {
		PageAction action = actionDispatcher.get(module, app, page);
		ModelAndView mav = new ModelAndView(WebUtil.PAGE_VIEW);
		mav.addObject(WebUtil.PAGE_ACTION, action);
		return mav;

	}

	@RequestMapping(value = "/index.stache", method = RequestMethod.GET)
	public void indexStache(HttpServletResponse resp, @PathVariable String module, @PathVariable String app,
			@PathVariable String page) throws IOException {
		String path = MessageFormat.format(PATH_TEMPLATE, module, app, page);
		Resource res = this.applicationContext.getResource(path);
		if (res.exists()) {
			render(resp, path, res);

		} else {
			resp.sendError(404);
		}

	}

	private void render(HttpServletResponse resp, String path, Resource res) throws IOException {
		long length = res.contentLength();
		if (length < Integer.MAX_VALUE && length > 0) {
			resp.setContentLength((int) length);
		}
		String mime = applicationContext.getServletContext().getMimeType(res.getFilename());
		if (mime != null) {
			resp.setContentType(mime);
		}
		resp.setCharacterEncoding("utf-8");
		InputStream in = res.getInputStream();
		try {
			StreamUtils.copy(in, resp.getOutputStream());
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
		}
	}

	@RequestMapping(value = "/do/{action}", method = RequestMethod.POST)
	@ResponseBody
	public Object execute(@PathVariable String action, @RequestBody String json) {
		return null;
	}

	@Autowired
	IActionDispatcher actionDispatcher;
	WebApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (WebApplicationContext) applicationContext;
	}
}
