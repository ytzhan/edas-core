package com.cntaiping.tpi.edas.wechat.rpc;

public interface WechatRpc {
	public String getAppId(String CtrlClass);

	public String getAccessToken(String CtrlClass);

	public String getUserCode(String CtrlClass, String code);
}
