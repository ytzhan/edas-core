package com.cntaiping.tpi.edas.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityFunction;

public class ActionWrapper {
	PageAction action;
	Class<?> actionClazz;
	String actionName;

	Map<String, Method> entityFunctions = new HashMap<String, Method>();

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

		for (Method method : actionClazz.getMethods()) {
			EntityFunction ef = null;
			if ((ef = method.getAnnotation(EntityFunction.class)) != null) {
				entityFunctions.put(ef.name(), method);
			}

		}

	}

	public Object execute(String command, String json) {
		try {

			Method method = entityFunctions.get(command);
			return method.invoke(action, json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(action.getClass().getName() + " method " + command + " execute error!");
		}
	}

	public String getActionName() {
		return actionName;
	}
}
