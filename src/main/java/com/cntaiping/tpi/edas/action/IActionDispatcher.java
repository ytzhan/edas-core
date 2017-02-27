package com.cntaiping.tpi.edas.action;

public interface IActionDispatcher {
	public PageAction get(String module, String app, String page);
}
