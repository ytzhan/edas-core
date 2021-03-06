package com.cntaiping.tpi.edas.web.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cntaiping.tpi.edas.action.ActionWrapper;
import com.cntaiping.tpi.edas.action.IActionDispatcher;
import com.cntaiping.tpi.edas.util.WebUtil;

@Controller
@RequestMapping("/gw/{module}/{app}/{scene}")
public class AppController {
	public static final String APP_HOMEPAGE = "/{0}/{1}/{2}/{3}";
	public static final String PAGE_PATH = "/{0}/{1}/{2}/{3}";

	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView home(@PathVariable String module, @PathVariable String app, @PathVariable String scene) {
		String view = MessageFormat.format(APP_HOMEPAGE, module, app,scene,  "index.html");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}

	@RequestMapping(value="/{page}.stache",method=RequestMethod.GET)
	public ModelAndView pageStache(@PathVariable String module, @PathVariable String app, @PathVariable String scene, @PathVariable String page) throws IOException {
		String view = MessageFormat.format(PAGE_PATH, module, app,scene, page+".stache");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}

	@RequestMapping(value="/{page}.js",method=RequestMethod.GET)
	public ModelAndView pageJs(HttpServletResponse resp, @PathVariable String module, @PathVariable String app,@PathVariable String scene,
			@PathVariable String page) throws IOException {
		ActionWrapper pa = actionDispatcher.get(module,app,scene,page);
		String view = MessageFormat.format(PAGE_PATH, module, app,scene, page+ ".js");
		ModelAndView mav = new ModelAndView(view);
		mav.addObject(WebUtil.PAGE_ACTION_CLASS, pa);
		mav.addObject(WebUtil.PAGE_ACTION, page);
		return mav;
	}

	@RequestMapping(value="/{page}/{command}",method=RequestMethod.POST)
	@ResponseBody
	public Object postPageAction(@PathVariable String module, @PathVariable String app, @PathVariable String scene, @PathVariable String page,
			 @PathVariable String command, @RequestBody String json) throws IOException {
		ActionWrapper pa = actionDispatcher.get(module,app,scene,page);
		return pa.execute(command, json);
	}

	@RequestMapping(value="/{page}/{command}",method=RequestMethod.GET)
	@ResponseBody
	public Object getPageAction(@PathVariable String module, @PathVariable String app, @PathVariable String scene, @PathVariable String page,
			 @PathVariable String command) throws IOException {
		System.out.println("run "+command);
		ActionWrapper pa = actionDispatcher.get(module,app,scene,page);
		return  pa.execute(command, "");
	}
	
	@Autowired
	IActionDispatcher actionDispatcher;
}
