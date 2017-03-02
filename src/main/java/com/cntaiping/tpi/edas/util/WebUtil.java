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
	public static final String PAGE_ACTION_CLASS="_PAGE_ACTION_CLASS";
	public static final String WEB_ROOT="/templates";
	public static final String WEB_PKG_ROOT="com.cntaiping.tpi.web.";
	public static final int WEB_PKG_ROOT_LEN=WEB_PKG_ROOT.length();
	
}
