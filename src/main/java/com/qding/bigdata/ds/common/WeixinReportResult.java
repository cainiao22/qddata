package com.qding.bigdata.ds.common;

public enum WeixinReportResult {

	DATA_NOT_READY(1, "数据没有生成"), DATA_NOT_VALID(2, "数据值不合理"),
	SUCCESS(0,"数据正常");
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private WeixinReportResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
