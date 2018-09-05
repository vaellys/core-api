package com.combanc.core.api;

import java.io.Serializable;

/**
 * @ClassName: RestResponse
 * @Description: TODO
 * @author qianguobing
 * @date 2018年8月20日 下午4:59:12
 */
public class RestResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code = Code.optsuccess.getCode();
	
	private T result;
	
	private String message = Code.optsuccess.getName();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
	
}
