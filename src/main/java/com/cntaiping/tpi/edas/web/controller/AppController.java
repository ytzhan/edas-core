package com.cntaiping.tpi.edas.web.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cntaiping.tpi.edas.action.IActionDispatcher;
import com.cntaiping.tpi.edas.action.PageAction;
import com.cntaiping.tpi.edas.util.WebUtil;

@Controller
@RequestMapping("/gw/{module}/{app}")
public class AppController {
	public static final String APP_HOME = "/{0}/{1}/{2}";
	public static final String PAGE_PATH = "/{0}/{1}/{2}/{3}";
	public static final String ACTION_PATH = "com.cntaiping.tpi.web.{0}.{1}.{2}.{3}";

	@RequestMapping("/home")
	public ModelAndView home(@PathVariable String module, @PathVariable String app) {
		String view = MessageFormat.format(APP_HOME, module, app, "index.html");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}

	@RequestMapping("/{page}/{action}.stache")
	public ModelAndView pageStache(@PathVariable String module, @PathVariable String app, @PathVariable String page,
			@PathVariable String action) throws IOException {
		String view = MessageFormat.format(PAGE_PATH, module, app, page, action + ".stache");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}

	@RequestMapping("/{page}/{action}.js")
	public ModelAndView pageJs(HttpServletResponse resp, @PathVariable String module, @PathVariable String app,
			@PathVariable String page,@PathVariable String action) throws IOException {
		String path = MessageFormat.format(ACTION_PATH, module, app, page,action);
		PageAction pa = actionDispatcher.get(path);
		String view = MessageFormat.format(PAGE_PATH, module, app, page ,action+  ".js");
		ModelAndView mav = new ModelAndView(view);
		mav.addObject(WebUtil.PAGE_ACTION_CLASS, pa);
		mav.addObject(WebUtil.PAGE_ACTION, page+"/"+action);
		return mav;
	}

	@RequestMapping("/{page}/{action}/{command}")
	@ResponseBody
	public Object pageAction(@PathVariable String module, @PathVariable String app, @PathVariable String page,
			@PathVariable String action,@PathVariable String command, @RequestBody String json) throws IOException {
		String path = MessageFormat.format(ACTION_PATH, module, app, page,action);
		PageAction pa = actionDispatcher.get(path);
		return pa.execute(command, json);
	}

	@Autowired
	IActionDispatcher actionDispatcher;
}
