package com.cntaiping.tpi.edas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cntaiping.tpi.edas.web.remote.MessageProcessor;
import com.cntaiping.tpi.edas.web.view.CustomViewResolver;
import com.cntaiping.tpi.edas.wechat.rpc.WechatRpc;
import com.cntaiping.tpi.edas.wechat.rpc.impl.LocalWechatRpcImpl;

@Configuration
@EnableConfigurationProperties({ WebProperties.class, WechatProperties.class })
public class WebConfiguration extends WebMvcConfigurerAdapter {
	public WebConfiguration() {
		System.out.println("WebConfiguration  running start .....");
	}

	@Bean
	public ViewResolver customViewResolver() {
		return new CustomViewResolver();
	}

	@Bean
	@ConditionalOnMissingBean(WechatRpc.class)
	public WechatRpc wechatRpc() {
		return new LocalWechatRpcImpl(wechatProperties);
	}

	@Autowired
	WechatProperties wechatProperties;
	@Autowired
	WebProperties webProperties;
	List<HttpMessageConverter<?>> converters;
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor(webProperties))
				.addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		this.converters = converters;
		super.extendMessageConverters(converters);
	}
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {	
		HandlerMethodReturnValueHandler hmr= new MessageProcessor(converters);
		returnValueHandlers.add(hmr);
		super.addReturnValueHandlers(returnValueHandlers);
	}
}
