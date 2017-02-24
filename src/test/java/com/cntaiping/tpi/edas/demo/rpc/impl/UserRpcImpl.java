package com.cntaiping.tpi.edas.demo.rpc.impl;

import com.cntaiping.tpi.edas.annotation.RPC;
import com.cntaiping.tpi.edas.demo.dto.UserDto;
import com.cntaiping.tpi.edas.demo.rpc.UserRpc;
@RPC
public class UserRpcImpl implements UserRpc {

	@Override
	public UserDto findOne(Long id) {
		UserDto user = new UserDto();
		user.setId(id);
		return user;
	}

}
