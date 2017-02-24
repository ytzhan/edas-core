package com.cntaiping.tpi.edas.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cntaiping.tpi.edas.demo.dto.UserDto;
import com.cntaiping.tpi.edas.demo.service.UserService;
import com.cntaiping.tpi.edas.web.BaseController;
import com.cntaiping.tpi.edas.web.RemoteResult;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@ResponseBody
	@RequestMapping("/{id}")
	public RemoteResult find(@PathVariable Long id) {
		UserDto user = userService.findOne(id);
		return new RemoteResult(user);
	}

	@Autowired
	UserService userService;

}
