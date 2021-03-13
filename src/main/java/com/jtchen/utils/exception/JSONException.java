package com.jtchen.utils.exception;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 14:42
 */
public class JSONException extends RuntimeException {

	public JSONException() {
	}

	public JSONException(String message) {
		super(message);
	}

	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
}
