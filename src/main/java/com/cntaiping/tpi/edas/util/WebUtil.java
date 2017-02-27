package com.cntaiping.tpi.edas.util;

import javax.servlet.http.HttpServletRequest;

public final class WebUtil {
	public static String getCurUrl(HttpServletRequest req,String protocol) {
		String host = req.getHeader("Host");
		String url = req.getRequestURI();
		StringBuilder sb = new StringBuilder();
		sb.append(protocol).append("://").append(host).append(url);
		return sb.toString();
	}
	
	public static final String PAGE_ACTION="_PAGE_ACTION";
	
	public static final String PAGE_VIEW="_PAGE_VIEW";
}
