package com.cntaiping.tpi.edas.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cntaiping.tpi.edas.demo.dto.UserDto;
import com.cntaiping.tpi.edas.demo.mapper.User2Mapper;
import com.cntaiping.tpi.edas.demo.mapper.UserMapper;
import com.cntaiping.tpi.edas.demo.mapper.po.UserPo;
import com.cntaiping.tpi.edas.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	@Transactional
	public UserDto findOne(Long id) {
		//UserPo po = userMapper.selectById(id);
		UserPo param = new UserPo();
		param.setId(id);
//		UserPo po = user2Mapper.findOne(param);
		UserDto dto = new UserDto();
//		BeanUtils.copyProperties(po, dto);
		return dto;
	}

//	@Resource
//	private UserMapper userMapper;
//	@Resource
//	private User2Mapper user2Mapper;
//	
//	
//	public void setUser2Mapper(User2Mapper user2Mapper) {
//		this.user2Mapper = user2Mapper;
//	}
//	public void setUserMapper(UserMapper userMapper) {
//		this.userMapper = userMapper;
//	}

}
