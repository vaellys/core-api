package com.combanc.core.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.combanc.core.api.ApiException;
import com.combanc.core.api.Code;
import com.combanc.core.api.RestResponse;

public class CustomerExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(CustomerExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		RestResponse<String> errInfo = new RestResponse<String>();
		errInfo.setMessage(ex.getMessage());
		if (ex instanceof ApiException) {
			ApiException apiException = (ApiException) ex;
			errInfo.setCode(apiException.getCode());
		} else {
			errInfo.setCode(Code.UnknownError.getCode());
		}
		errInfo.setResult(request.getRequestURI().toString());
		try {
			response.getWriter().write(JSON.toJSONString(errInfo));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return mv;
	}

}
