package com.cntaiping.tpi.edas.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cv")
public class CustomViewController {
	@RequestMapping("/test")
	public ModelAndView test(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test.cus");
		return mav;
	}
}
