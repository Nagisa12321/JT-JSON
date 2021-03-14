package com.jtchen.utils.exception;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 14:42
 */
public class JSONException extends RuntimeException {

	/*
		JSON异常
		一般用于JSON解析过程中解析失败抛出异常
	 */
	@SuppressWarnings("unused")
	public JSONException() {
	}

	public JSONException(String message) {
		super(message);
	}

	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
}
