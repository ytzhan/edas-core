package com.cntaiping.tpi.edas.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor(webProperties))
				.addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
