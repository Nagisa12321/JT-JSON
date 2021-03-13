package com.jtchen.json;

import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 19:36
 */
public abstract class AbstractJSON implements JSON {

	private JSONFactory parser = new LinkedJSONFactory();

	/**
	 * 函数功能: 解析json数据, 将字符串转化为JSON对象
	 *
	 * @param s 待解析json字符串
	 * @return JSON对象
	 */
	public static JSON parse(String s) {
		return null;
	}

	public String toString() {
		return parser.unformattedParse(this);
	}



}
