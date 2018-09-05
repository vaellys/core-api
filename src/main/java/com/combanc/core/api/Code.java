package com.combanc.core.api;

public enum Code {
	optsuccess("操作成功", "000"),
	opterror("操作失败", "001"),
	pwderror("密码错误", "002"),
	UnauthorizedRequest("未授权请求或授权失效", "003"),
	NotExistUser("用户不存在", "004"),
	NotExistAssets("无此资产", "005"),
	ParamsError("无效参数【参数验证不正确或参数匹配不成功等情况】", "006") , 
	ErrorCode("无效的二维码", "007"),
	UnknownError("未知错误", "-1"),
	NetworkError("网络异常", "500"),;
	private String name;
	private String code;
	
	private Code(String name,String code) {
		this.name=name;
		this.code=code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
