package com.cntaiping.tpi.edas.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cntaiping.tpi.edas.util.WebUtil;
import com.cntaiping.tpi.edas.wechat.rpc.WechatRpc;

public abstract class WechatIndexController extends BaseIndexController {

	public static final String OAUTH_ADDR = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp)
			throws BaseException, Exception {
		String code = req.getParameter("code");
		String userCode = null;
		if (StringUtils.isEmpty(code)) {
			return redirectOAuth(req, resp);
		} else {
			userCode = wechatRpc.getUserCode(this.getClass().getName(), code);
		}
		if (StringUtils.isEmpty(userCode)) {
			return redirectOAuth(req, resp);
		}
		ModelAndView mav = home(req);
		createSign(mav, userCode);
		return mav;
	}
	@ModelAttribute
	public void checkSign(HttpServletRequest req, HttpServletResponse resp){
		
		
	}

	private void createSign(ModelAndView mav, String userCode) {
		Map<String, Object> session = new HashMap<String, Object>();
		String timeStamp = String.valueOf(System.currentTimeMillis());
		session.put("userCode", userCode);
		session.put("timeStamp", timeStamp);
		String origin = userCode + timeStamp + webProperties.getSignToken();
		String sign = DigestUtils.sha512Hex(origin);
		session.put("sign", sign);
		mav.addObject("session", session);
	}

	private ModelAndView redirectOAuth(HttpServletRequest req,
			HttpServletResponse resp) throws UnsupportedEncodingException,
			IOException {
		String appId = wechatRpc.getAppId(this.getClass().getName());
		String curUrl = WebUtil.getCurUrl(req, webProperties.getProtocol());
		String redirectUrl = String.format(OAUTH_ADDR, appId,
				URLEncoder.encode(curUrl, "UTF-8"));
		resp.getWriter().append(redirectUrl);
		return null;
	}

	@Autowired
	WebProperties webProperties;
	@Autowired
	WechatRpc wechatRpc;
}
