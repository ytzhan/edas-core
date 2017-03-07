package com.cntaiping.tpi.edas.action;

import java.util.HashMap;
import java.util.Map;

import com.cntaiping.tpi.edas.action.validator.Errors;
import com.cntaiping.tpi.edas.action.validator.ValidatorDef;
import com.cntaiping.tpi.edas.action.validator.ValidatorFactory;

public abstract class EntityValidator {
	private ValidatorFactory validatorFactory;
	private Map<String, ValidatorDef> validatorDefs = new HashMap<String, ValidatorDef>(8);

	public void init(ValidatorFactory vf) {
		this.validatorFactory = vf;
		config();
	}

	public abstract void config();

	public void register(String path, String validator, Object... params) {
		ValidatorDef def = validatorDefs.get(path);
		if (def == null) {
			def = new ValidatorDef(path);
			validatorDefs.put(path, def);
		}
		def.addValidatorCfg(validatorFactory, validator, params);
	}

	public Errors valid(Object target) {
		Errors errors = new Errors();

		return errors;
	}

}
