package com.jtchen.factory.impl;

import com.jtchen.factory.JSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.json.impl.LinkedJSON;
import com.jtchen.utils.exception.JSONException;
import com.jtchen.utils.parsers.JSONParser;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/11 12:01
 */
public class LinkedJSONFactory extends JSONFactory {

	// 采用单例模式: 因为解析器只需要一个即可
	private static final LinkedJSONFactory factory = new LinkedJSONFactory();

	public static JSONFactory getInstance() {
		return factory;
	}

	private LinkedJSONFactory() {}

	/**
	 * 专门生产LinkedJSON的工厂, 该工厂继承JSONFactory,
	 * 专门告诉父类应该生产LinkedJSON
	 *
	 * @param s 字符串
	 * @return 生产出来的JSON实例
	 */
	@Override
	public JSON parse(String s) {
		// 在JSON解析器中注册相应的工厂, 好让JSON解析器委托生产
		/*

		为什么不用JSON解析器直接生产?

		 - JSON解析器包括了很多解析方法, 并且继承了Parser接口, 明摆着就是一个解析器
			只不过跟别的解析器生产的不同, 它生产的是JSON对象而已。
			里面很多的解析方法不应该对用户暴露, 用户只需要一个parse()方法即可

		仅调用JSONParser的解析函数进行解析,
		JSONParser的解析函数会相应的调用其他解析类进行解析
		 */
		JSONParser jsonParser = new JSONParser(this);
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			if (!jsonParser.parse(ch)) throw new JSONException("illegal character: " + ch);
		}
		return jsonParser.commit();
	}


	// 制造一个空的JSON对象
	@Override
	public JSON createJSON() {
		return new LinkedJSON();
	}
}
