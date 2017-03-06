package com.cntaiping.tpi.edas.web.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;


public abstract class BaseValidator implements IValidator {
	protected String path;
	protected String[] steps;

	@Override
	public void init(String path, Object[] args) {
		this.path = path;
		this.steps = path.split("\\.");
		parseParam(args);
	}

	protected void parseParam(Object[] args) {
	}

	@Override
	public void validate(Object data, Errors error) {
		validateObject(0, data, "", error);

	}

	protected void validateList(int stepIdx, List<?> list, String parent, Errors error) {
		for(int i =0;i<list.size();i++){
			Object stepData = list.get(i);
			String curRoute =parent+"["+i+"]";
			validElement(stepIdx, error, stepData, curRoute);
		}
	}

	protected void validateObject(int stepIdx, Object data, String parent, Errors error) {
		String step = steps[stepIdx];
		Object stepData = null;
		try {
			stepData = PropertyUtils.getProperty(data, step);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		String curRoute = parent.length() > 0 ? (parent + "." + step) : step;
		validElement(stepIdx, error, stepData, curRoute);
	}

	private void validElement(int stepIdx, Errors error, Object stepData, String curRoute) {
		if (stepData != null) {
			if (stepData instanceof List) {
				validateList(stepIdx, (List<?>) stepData, curRoute, error);
			} else if (++stepIdx < steps.length) {
				validateObject(stepIdx, stepData, curRoute, error);
			} else {
				validateMetaData(curRoute, stepData,error);
			}
		} else {
			validateMetaData(curRoute, stepData,error);
		}
	}

	protected abstract void validateMetaData(String route, Object data,Errors error);

}
