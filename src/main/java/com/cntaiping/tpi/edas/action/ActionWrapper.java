package com.cntaiping.tpi.edas.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.NullClass;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionWrapper {
	public String getDefaultEntityMethodName() {
		return defaultEntityMethodName;
	}

	public Method getDefaultEntityMethod() {
		return defaultEntityMethod;
	}

	private PageAction action;
	private Class<?> actionClazz;
	private String actionName;

	private Map<String, Method> entityEvents = new HashMap<String, Method>();
	private Map<String, Method> remoteMethods = new HashMap<String, Method>();
	private Map<String, Class> remoteMethodParams = new HashMap<String, Class>();
	private Map<String, Method> entityMethods = new HashMap<String, Method>();
	private String defaultEntityMethodName=null;
	private Method defaultEntityMethod=null;

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
			this.actionClazz=annotation.entity();
		} else {
			String clazzName = actionClazz.getSimpleName();
			if (clazzName.endsWith("Action")) {
				clazzName = clazzName.substring(0, clazzName.length() - 6);
			}
			this.actionName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
		}

		for (Method method : actionClazz.getMethods()) {
			if (method.getAnnotation(EntityEvent.class) != null) {
				EntityEvent ee=method.getAnnotation(EntityEvent.class);
				entityEvents.put(ee.name(), method);
			}else if (method.getAnnotation(RemoteFunction.class) != null){
				RemoteFunction rf=method.getAnnotation(RemoteFunction.class);
				remoteMethods.put(rf.name(), method);
				remoteMethodParams.put(rf.name(), rf.param());
			}else if (method.getAnnotation(EntityFunction.class) != null){
				EntityFunction ef=method.getAnnotation(EntityFunction.class);
				if (ef.getClass()==NullClass.class){
					defaultEntityMethodName=ef.name();
					defaultEntityMethod=method;
				}else
					remoteMethods.put(ef.name(), method);
			}

		}

	}
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	private Object jsonToObject(String json,Class clazz){
		try{
			return objectMapper.readValue(json, clazz);
		}catch (Exception ex){
			throw new RuntimeException("序列化\n\t"+ json +"\n为"+clazz.getName()+" 错误！");
		}
	}

	public Object execute(String command, String json) {
		try {
			if (command.equalsIgnoreCase(defaultEntityMethodName))
				return defaultEntityMethod.invoke(action);
			else{
				Method method = entityEvents.get(command);
				if (method!=null)
					return method.invoke(action, jsonToObject(json,actionClazz));
				else{
					method = entityMethods.get(command);
					if (method!=null)
						return method.invoke(action, jsonToObject(json,actionClazz));
					else{
						method = remoteMethods.get(command);
						if (method!=null)
							return method.invoke(action, jsonToObject(json,remoteMethodParams.get(command)));
					}
				}
			}
			throw new RuntimeException(action.getClass().getName()+"没有实现"+command+"方法");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(action.getClass().getName() + " method " + command + " execute error!");
		}
	}
	
	public Object defaultEntity(){
		try {
			if (defaultEntityMethod!=null)	
				return defaultEntityMethod.invoke(action);
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(action.getClass().getName() + " method " + defaultEntityMethodName + " execute error!");
		}
	}
	
	public String[] getEntityEvents(){
		return entityEvents.keySet().toArray(new String[0]);
	}
	
	public String[] getEntityMethods(){
		return entityMethods.keySet().toArray(new String[0]);
	}
	
	public String[] getRemoteMethods(){
		return remoteMethods.keySet().toArray(new String[0]);
	}

	public String getActionName() {
		return actionName;
	}
}
