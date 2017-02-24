package com.cntaiping.tpi.edas.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tp.web")
public class WebProperties {
	private String protocol = "http";

	private String signToken = "signToken";

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSignToken() {
		return signToken;
	}

	public void setSignToken(String signToken) {
		this.signToken = signToken;
	}
}
