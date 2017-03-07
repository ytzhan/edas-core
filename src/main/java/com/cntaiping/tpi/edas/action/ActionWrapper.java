package com.cntaiping.tpi.edas.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.cntaiping.tpi.edas.action.Result.STATUS;
import com.cntaiping.tpi.edas.action.validator.ValidatorFactory;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.annotation.EntityEvent;
import com.cntaiping.tpi.edas.annotation.EntityFunction;
import com.cntaiping.tpi.edas.annotation.NullClass;
import com.cntaiping.tpi.edas.annotation.RemoteFunction;
import com.cntaiping.tpi.edas.web.BaseRuntimeException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionWrapper {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public String getDefaultEntityMethodName() {
		return defaultEntityMethodName;
	}

	public Method getDefaultEntityMethod() {
		return defaultEntityMethod;
	}

	private Object action;
	private Class<?> entityClazz;
	private EntityValidator<?> validator;
	private String actionName;

	private Map<String, Method> entityEvents = new HashMap<String, Method>();
	private Map<String, Method> remoteMethods = new HashMap<String, Method>();
	private Map<String, Class<?>> remoteMethodParams = new HashMap<String, Class<?>>();
	private Map<String, Method> entityMethods = new HashMap<String, Method>();
	private String defaultEntityMethodName = null;
	private Method defaultEntityMethod = null;

	private ValidatorFactory validatorFactory;

	public Object getAction() {
		return action;
	}

	public ActionWrapper(Object action, ValidatorFactory validatorFactory) {
		Assert.notNull(action, "Action 不能为空");
		this.action = action;
		this.validatorFactory = validatorFactory;
		init();
	}

	public void init() {
		Action annotation = action.getClass().getAnnotation(Action.class);
		this.actionName = annotation.page();
		this.entityClazz = annotation.entity();
		try {
			this.validator = annotation.validator().newInstance();
			this.validator.init(this.validatorFactory);
		} catch (Exception e) {
			throw new BaseRuntimeException("校验器初始化失败", e);
		}
		for (Method method : action.getClass().getMethods()) {
			if (method.getAnnotation(EntityEvent.class) != null) {
				EntityEvent ee = method.getAnnotation(EntityEvent.class);
				entityEvents.put(ee.name(), method);
			} else if (method.getAnnotation(RemoteFunction.class) != null) {
				RemoteFunction rf = method.getAnnotation(RemoteFunction.class);
				remoteMethods.put(rf.name(), method);
				remoteMethodParams.put(rf.name(), rf.param());
			} else if (method.getAnnotation(EntityFunction.class) != null) {
				EntityFunction ef = method.getAnnotation(EntityFunction.class);
				if (ef.name().length() == 0) {
					defaultEntityMethodName = ef.name();
					defaultEntityMethod = method;
				} else {
					entityMethods.put(ef.name(), method);
				}
			}

		}

		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private ObjectMapper objectMapper;

	private Object jsonToObject(String json, Class<?> clazz) {
		try {
			return objectMapper.readValue(json, clazz);

		} catch (Exception ex) {
			logger.error("序列化{}为{}错误", json, clazz, ex);
			throw new RuntimeException("序列化\n\t" + json + "\n为" + clazz.getName() + " 错误！");
		}
	}

	public Result execute(String command, String json) {
		Result result = new Result();
		try {
			if (command.equalsIgnoreCase(defaultEntityMethodName))
				return result.attachData(defaultEntityMethod.invoke(action));
			else {
				Method method = entityEvents.get(command);
				if (method == null) {
					method = entityMethods.get(command);
				}
				if (method != null) {
					Object entity = jsonToObject(json, entityClazz);
					this.validator.valid(entity,result);
					if(result.hasErrors()){
						return result.setStatus(STATUS.VALID_ERROR);
					}
					return result.attachData(method.invoke(action, entity));
				} else {
					method = remoteMethods.get(command);
					if (method != null) {
						Class<?> c = remoteMethodParams.get(command);
						if (c == NullClass.class)
							return result.attachData(method.invoke(action));
						else
							return result.attachData(method.invoke(action, jsonToObject(json, remoteMethodParams.get(command))));
					}
				}
			}
			return result.setStatus(STATUS.SYS_ERROR).rejectError("S001", action.getClass().getName() + "没有实现" + command + "方法");
			//throw new RuntimeException(action.getClass().getName() + "没有实现" + command + "方法");
		} catch (Exception e) {
			logger.error(action.getClass().getName() + " method " + command + " execute error!", e);
			return result.setStatus(STATUS.SYS_ERROR).rejectError("S002", e.getMessage());
		}
	}

	public Object _execute(String command, String json) {
		try {
			if (command.equalsIgnoreCase(defaultEntityMethodName))
				return defaultEntityMethod.invoke(action);
			else {
				Method method = entityEvents.get(command);
				if (method != null)
					return method.invoke(action, jsonToObject(json, entityClazz));
				else {
					method = entityMethods.get(command);
					if (method != null)
						return method.invoke(action, jsonToObject(json, entityClazz));
					else {
						method = remoteMethods.get(command);
						System.out.println(command + " : " + method);
						if (method != null) {
							Class<?> c = remoteMethodParams.get(command);
							if (c == NullClass.class)
								return method.invoke(action);
							else
								return method.invoke(action, jsonToObject(json, remoteMethodParams.get(command)));
						} else {
							return method.invoke(command);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(action.getClass().getName() + " method " + command + " execute error!");
		}
	}

	public Object defaultEntity() {
		try {
			if (defaultEntityMethod != null)
				return defaultEntityMethod.invoke(action);
			else {
				Object obj = entityClazz.newInstance();
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					action.getClass().getName() + " method " + defaultEntityMethodName + " execute error!");
		}
	}

	public String[] getEntityEvents() {
		return entityEvents.keySet().toArray(new String[0]);
	}

	public String[] getEntityMethods() {
		return entityMethods.keySet().toArray(new String[0]);
	}

	public String[] getRemoteMethods() {
		return remoteMethods.keySet().toArray(new String[0]);
	}

	public String getActionName() {
		return actionName;
	}
}
