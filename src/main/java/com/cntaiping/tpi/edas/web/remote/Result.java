package com.cntaiping.tpi.edas.web.remote;

public class Result {

	private boolean status;

	private StringBuilder message = new StringBuilder();

	private Object data;

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message.toString();
	}

	public boolean isStatus() {
		return status;
	}

	public Result(Object data) {
		this.status = true;
		this.data = data;
	}

	public Result(boolean status) {
		this.status = status;
	}

	public Result(String code, String msg) {
		this.status = false;
		message.append(code).append(":").append(msg);
	}

	public void appendError(String code, String msg) {
		if (message.length() > 0) {
			message.append(";");
		}
		message.append(code).append(":").append(msg);
	}
	public void appendError(String msg) {
		if (message.length() > 0) {
			message.append(";");
		}
		message.append(msg);
	}

	public void attachData(Object data) {
		this.data = data;
	}
}
