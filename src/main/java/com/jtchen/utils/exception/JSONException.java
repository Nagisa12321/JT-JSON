package com.jtchen.utils.exception;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 14:42
 */
public class JSONException extends RuntimeException {

	/*
		JSON�쳣
		һ������JSON���������н���ʧ���׳��쳣
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
