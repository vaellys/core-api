package com.combanc.core.api.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.combanc.core.api.ApiException;
import com.combanc.core.api.Code;
import com.combanc.core.api.util.ApiSigner;
import com.combanc.core.api.util.RequestUtil;
import com.combanc.core.api.util.StringUtil;

/**
 * @ClassName: AuthenticationInterceptor
 * @Description: 接口签名认证
 * @author qianguobing
 * @date 2018年8月27日 上午10:41:12
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Value("${appKey:}")
	private String appKey;
	
	@Value("${appSecret:}")
	private String appSecret;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		if(StringUtil.isBlank(appKey) || StringUtil.isBlank(appSecret)) {
			throw new ApiException(Code.UnauthorizedRequest.getCode(), "应用appKey和appSecret未初始化！");
		}
		//获取APPKEY和客户端传过来的签名
		String reqAppKey = request.getParameter("appKey");
		String reqSign = request.getParameter("sign");
		//检查appKey
		if(StringUtil.isBlank(reqAppKey) || !StringUtil.lowerCase(reqAppKey).equals(appKey)) {
			throw new ApiException(Code.UnauthorizedRequest.getCode(), "无效的AppKey！");
		}
		//构建签名
		Map<String, Object> paramMaps = RequestUtil.convertRequestParamsToMap(request);
		paramMaps.remove("sign");
		String buildSign = ApiSigner.buildSign(paramMaps, appSecret);
		if(!ApiSigner.verify(buildSign, reqSign)) {
			throw new ApiException(Code.UnauthorizedRequest.getCode(), "无效的签名！");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
	}  
		

	@Override
	@Transactional(readOnly = false)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
	}

}
