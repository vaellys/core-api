package com.combanc.core.api;

public class ApiException extends Exception {

	private static final long serialVersionUID = 1115638422426706901L;

	private String code = "000";
	
	public ApiException(Exception exp) {
		super(exp);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApiException(String message, Exception exp) {
		super(message + exp.getMessage());
	}

	public ApiException(String code, String message, Exception exp) {
		super(message + exp.getMessage());
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
