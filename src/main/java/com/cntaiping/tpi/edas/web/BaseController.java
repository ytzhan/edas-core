package com.cntaiping.tpi.edas.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseController {
	@InitBinder
	void initBinder(WebDataBinder binder) {
		validators(binder);
	}
	protected void validators(WebDataBinder binder) {
	};
	
	@ExceptionHandler(BaseRuntimeException.class)
	public @ResponseBody com.cntaiping.tpi.edas.web.RemoteResult runtimeExceptionHandler(BaseRuntimeException runtimeException) {
		com.cntaiping.tpi.edas.web.RemoteResult rr = new com.cntaiping.tpi.edas.web.RemoteResult(false);
		rr.appendError(runtimeException.getMessage());
		return rr;
	}
	@ExceptionHandler(BaseException.class)
	public @ResponseBody com.cntaiping.tpi.edas.web.RemoteResult exceptionHandler(BaseException runtimeException) {
		com.cntaiping.tpi.edas.web.RemoteResult rr = new com.cntaiping.tpi.edas.web.RemoteResult(false);
		rr.appendError(runtimeException.getMessage());
		return rr;
	}
	
	protected RemoteResult errOutput(BindingResult result){
		RemoteResult rr = new RemoteResult(false);
		for(ObjectError error:result.getAllErrors()){
			rr.appendError(error.getCode(), error.getDefaultMessage());
		}
		return rr;
	}
}
