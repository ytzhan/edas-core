package com.cntaiping.tpi.edas.demo.rpc;

import com.cntaiping.tpi.edas.demo.dto.UserDto;

public interface UserRpc {
	public UserDto findOne(Long id);
}
