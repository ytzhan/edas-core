package com.cntaiping.tpi.edas.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cntaiping.tpi.edas.web.RemoteResult;
import com.cntaiping.tpi.edas.web.WechatIndexController;
@Controller
@RequestMapping("/wxmall")
public class WxMallController extends WechatIndexController{
	@RequestMapping("/sms")
	@ResponseBody
	public RemoteResult sms(){
		return new RemoteResult(true);
	}
}
