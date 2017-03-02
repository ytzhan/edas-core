package com.cntaiping.tpi.edas.action;

import java.util.HashMap;
import java.util.Map;

public class SceneDef {
	private String module;
	private String app;
	private String scene;

	private Map<String, ActionWrapper> actions = new HashMap<String, ActionWrapper>(32);

	public String getModule() {
		return module;
	}

	public String getApp() {
		return app;
	}

	public String getScene() {
		return scene;
	}

	public SceneDef(String module, String app, String scene) {
		this.module = module;
		this.app = app;
		this.scene = scene;
	}
	public Map<String, ActionWrapper> getActions() {
		return actions;
	}
	public ActionWrapper getAction(String actionName) {
		return actions.get(actionName);
	}
	public void addAction(ActionWrapper action){
		this.actions.put(action.getActionName(), action);
	}

}
