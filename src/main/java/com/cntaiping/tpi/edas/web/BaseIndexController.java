package com.cntaiping.tpi.edas.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseIndexController extends BaseController implements
		ApplicationContextAware {

	private String PATH_ROOT = "classpath:/templates/";
	
	private String protocol="http://";
	
	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest req) throws BaseException {
		ModelAndView mav = new ModelAndView();
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		if (rm != null && rm.value().length == 1) {
			String path = rm.value()[0] + "/index";
			path = path.replace("//", "/");
			mav.setViewName(path);
		}

		doIndex(req, mav);
		return mav;
	}

	protected void initDataJson(Object data,ModelAndView mav) throws BaseException{
		StringWriter wr = new StringWriter();
		try {
			mapper.writeValue(wr, data);
			mav.addObject("dataJson", wr.toString());
		} catch (Exception e) {
			throw new BaseException(e);
		}
		
	}
	
	/**
	 * 加载应用内的js/stache
	 * 
	 * @param req
	 * @param resp
	 * @param path
	 * @throws IOException
	 */
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public void resource(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		String path = req.getRequestURI();
		if (rm != null && rm.value().length == 1) {
			String mn = rm.value()[0];
			int idx = path.indexOf(mn);
			path = path.substring(idx);
		}
		path = PATH_ROOT + path;
		path = path.replace("//", "/");
		Resource res = this.applicationContext.getResource(path);
		if (res.exists()) {
			render(resp, path, res);

		} else {
			resp.sendError(404);
		}

	}

	private void render(HttpServletResponse resp, String path, Resource res)
			throws IOException {
		long length = res.contentLength();
		if (length < Integer.MAX_VALUE && length > 0) {
			resp.setContentLength((int) length);
		}
		String mime = applicationContext.getServletContext().getMimeType(
				res.getFilename());
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = (WebApplicationContext) applicationContext;
	}

	WebApplicationContext applicationContext;

	protected void doIndex(HttpServletRequest req, ModelAndView mav)
			throws BaseException {
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	};

}
