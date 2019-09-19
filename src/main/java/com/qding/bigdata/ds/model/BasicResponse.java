package com.qding.bigdata.ds.model;

public class BasicResponse {
	private int code ; //code=0 表示错误 // code=1 表示成功 code=2 表示以存在
	private String errMessage;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
