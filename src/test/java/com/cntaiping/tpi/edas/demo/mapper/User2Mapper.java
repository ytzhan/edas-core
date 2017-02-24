package com.cntaiping.tpi.edas.demo.mapper;

import com.cntaiping.tpi.edas.annotation.Mapper;
import com.cntaiping.tpi.edas.demo.mapper.po.UserPo;

@Mapper
public interface User2Mapper {
	public UserPo findOne(UserPo po);
}
