package com.cntaiping.tpi.edas.demo.service;

import com.cntaiping.tpi.edas.demo.dto.UserDto;


public interface UserService {
	public UserDto findOne(Long id);
}
