package com.cntaiping.tpi.edas.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cntaiping.tpi.edas.annotation.RemoteResult;
import com.cntaiping.tpi.edas.demo.dto.UserDto;
import com.cntaiping.tpi.edas.web.BaseController;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;

@Controller
@RequestMapping("/wxmall")
public class WxMallController extends BaseController {
	@RequestMapping("/sms")
	@RemoteResult
	public UserDto sms() {
		UserDto dto = new UserDto();
		dto.setId(123L);
		dto.setName("vidy");
		return dto;
	}
	@RequestMapping("/sms2")
	@RemoteResult
	public UserDto sms2() {
		throw new BaseRuntimeException("异常测试");
	}

	

}
