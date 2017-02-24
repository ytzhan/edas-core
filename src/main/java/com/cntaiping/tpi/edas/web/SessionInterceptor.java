package com.cntaiping.tpi.edas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor {
	WebProperties webProperties;

	public SessionInterceptor(WebProperties webProperties) {
		this.webProperties = webProperties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		if (handler instanceof HandlerMethod
				&& WechatIndexController.class
						.isAssignableFrom(((HandlerMethod) handler)
								.getBeanType()) && !path.endsWith("/index")) {
			System.out.println("Œ¢–≈«Î«Û¿πΩÿ");

		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
