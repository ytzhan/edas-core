package com.cntaiping.tpi.edas.web.remote;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import com.cntaiping.tpi.edas.annotation.RemoteBody;
import com.cntaiping.tpi.edas.annotation.RemoteResult;

public class MessageProcessor extends AbstractMessageConverterMethodProcessor {

	public MessageProcessor(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), RemoteResult.class)
				|| returnType.hasMethodAnnotation(RemoteResult.class));
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
		ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);
		if(! (returnValue instanceof com.cntaiping.tpi.edas.web.remote.Result)){
			returnValue = new com.cntaiping.tpi.edas.web.remote.Result(returnValue);
		}
		writeWithMessageConverters(returnValue, returnType, inputMessage, outputMessage);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RemoteBody.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		return null;
	}

}
