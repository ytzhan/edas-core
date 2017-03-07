package com.cntaiping.tpi.edas.action.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.cntaiping.tpi.edas.web.BaseRuntimeException;

public class ValidatorDef {
	private String path;
	protected String[] steps;
	private List<ValidatorCfg> cfgs = new ArrayList<ValidatorCfg>(4);
	private List<IValidator> insList = new ArrayList<IValidator>(4);

	public ValidatorDef(String path) {
		this.path = path;
		this.steps = path.split("\\.");
	}

	public String getPath() {
		return path;
	}
	

	public void addValidatorCfg(ValidatorFactory vf, String validatorName, Object... params) {
		ValidatorCfg cfg = new ValidatorCfg(validatorName, params);
		this.cfgs.add(cfg);
		this.insList.add(build(vf, cfg));
	}

	public IValidator build(ValidatorFactory vf, ValidatorCfg cfg) {
		Class<?> clazz = vf.getValidatorClazz(cfg.getValidatorName());
		try {
			IValidator ins = (IValidator) clazz.newInstance();
			ins.init(cfg.params);
			return ins;
		} catch (InstantiationException e) {
			throw new BaseRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new BaseRuntimeException(e);
		}
	}
	
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

	protected  void validateMetaData(String route, Object data,Errors error){
		for(IValidator ins:insList){
			ins.validate(route, data, error);
		}
	}

	class ValidatorCfg {
		private String validatorName;
		private Object[] params;

		public ValidatorCfg(String validatorName, Object... params) {
			this.validatorName = validatorName;
			this.params = params;
		}

		public String getValidatorName() {
			return validatorName;
		}

		public Object[] getParams() {
			return params;
		}
	}
}
