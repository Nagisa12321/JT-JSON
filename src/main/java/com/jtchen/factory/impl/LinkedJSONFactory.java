package com.jtchen.factory.impl;

import com.jtchen.json.AbstractJSON;
import com.jtchen.factory.JSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.json.impl.LinkedJSON;
import com.jtchen.utils.JSONParser;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/11 12:01
 */
public class LinkedJSONFactory extends JSONFactory {
	@Override
	public JSON parse(String s) {
		JSONParser jsonParser = new JSONParser(this);
		return jsonParser.parse(s);
	}

	@Override
	public AbstractJSON createJSON() {
		return new LinkedJSON();
	}
}
