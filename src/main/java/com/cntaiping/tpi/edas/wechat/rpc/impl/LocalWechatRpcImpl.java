package com.cntaiping.tpi.edas.wechat.rpc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cntaiping.tpi.edas.web.WechatProperties;
import com.cntaiping.tpi.edas.wechat.rpc.WechatRpc;

public class LocalWechatRpcImpl implements WechatRpc {

	private Logger log = LoggerFactory.getLogger(getClass());
	WechatProperties prop;

	public LocalWechatRpcImpl(WechatProperties prop) {
		this.prop = prop;
	}

	@Override
	public String getAppId(String CtrlClass) {
		log.warn("ctrl {} getAppId,return {}", CtrlClass, this.prop.getAppId());
		return this.prop.getAppId();
	}

	@Override
	public String getAccessToken(String CtrlClass) {
		return null;
	}

	@Override
	public String getUserCode(String CtrlClass, String code) {
		log.warn("ctrl {} {} getUserCode,return {}", CtrlClass,code, this.prop.getAppId(),this.prop.getUserCode());
		return this.prop.getUserCode();
	}

}
