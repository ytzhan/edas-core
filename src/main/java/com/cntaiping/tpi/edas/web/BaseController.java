package com.cntaiping.tpi.edas.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cntaiping.tpi.edas.web.remote.Result;

public abstract class BaseController {
	@InitBinder
	void initBinder(WebDataBinder binder) {
		validators(binder);
	}
	protected void validators(WebDataBinder binder) {
	};
	
	@ExceptionHandler(BaseRuntimeException.class)
	public @ResponseBody com.cntaiping.tpi.edas.web.remote.Result runtimeExceptionHandler(BaseRuntimeException runtimeException) {
		com.cntaiping.tpi.edas.web.remote.Result rr = new com.cntaiping.tpi.edas.web.remote.Result(false);
		rr.appendError(runtimeException.getMessage());
		return rr;
	}
	@ExceptionHandler(BaseException.class)
	public @ResponseBody com.cntaiping.tpi.edas.web.remote.Result exceptionHandler(BaseException runtimeException) {
		com.cntaiping.tpi.edas.web.remote.Result rr = new com.cntaiping.tpi.edas.web.remote.Result(false);
		rr.appendError(runtimeException.getMessage());
		return rr;
	}
	
	protected Result errOutput(BindingResult result){
		Result rr = new Result(false);
		for(ObjectError error:result.getAllErrors()){
			rr.appendError(error.getCode(), error.getDefaultMessage());
		}
		return rr;
	}
}
