package com.cntaiping.tpi.edas.action;

public interface IActionDispatcher {
	public ActionWrapper get(String module,String app,String scene,String action);
}
