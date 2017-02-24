package com.cntaiping.tpi.edas.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {
	@InitBinder
	void initBinder(WebDataBinder binder) {
		validators(binder);
	}
	protected void validators(WebDataBinder binder) {
	};
	
	protected RemoteResult errOutput(BindingResult result){
		RemoteResult rr = new RemoteResult(false);
		for(ObjectError error:result.getAllErrors()){
			rr.appendError(error.getCode(), error.getDefaultMessage());
		}
		return rr;
	}
}
