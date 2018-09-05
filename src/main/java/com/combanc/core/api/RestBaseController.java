package com.combanc.core.api;

/**
 * @ClassName: RestBaseController
 * @Description: TODO
 * @author qianguobing
 * @date 2018年8月20日 下午4:57:52
 */
public abstract class RestBaseController {

	protected <T> RestResponse<T> wrap(Code code, T result) {
		RestResponse<T> rr = new RestResponse<T>();
		rr.setCode(code.getCode());
		rr.setResult(result);
		return rr;
	}

	protected <T> RestResponse<T> wrap(Code code, String message) {
		RestResponse<T> rr = new RestResponse<T>();
		rr.setCode(code.getCode());
		rr.setMessage(message);
		return rr;
	}

	protected <T> RestResponse<T> wrap(Code code, String message, T result) {
		RestResponse<T> rr = new RestResponse<T>();
		rr.setCode(code.getCode());
		rr.setMessage(message);
		rr.setResult(result);
		return rr;
	}

}
