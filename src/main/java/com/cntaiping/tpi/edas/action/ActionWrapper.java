package com.cntaiping.tpi.edas.action;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.cntaiping.tpi.edas.annotation.Action;

public class ActionWrapper {
	PageAction action;
	Class<?> actionClazz;
	String actionName;
	
	public PageAction getAction() {
		return action;
	}

	public ActionWrapper(PageAction action) {
		Assert.notNull(action, "Action 不能为空");
		this.action = action;
		init();
	}

	public void init() {
		this.actionClazz = action.getClass();
		Action annotation = actionClazz.getAnnotation(Action.class);
		if (annotation != null && !StringUtils.isEmpty(annotation.value())) {
			this.actionName = annotation.value();
		} else {
			String clazzName = actionClazz.getSimpleName();
			if (clazzName.endsWith("Action")) {
				clazzName = clazzName.substring(0, clazzName.length() - 6);
			}
			this.actionName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
		}
	}

	public String getActionName() {
		return actionName;
	}
}
