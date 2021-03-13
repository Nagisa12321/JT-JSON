package com.jtchen.utils.parsers;

import com.jtchen.beans.JSONArray;
import com.jtchen.json.JSON;
import com.jtchen.utils.Parser;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 17:59
 */
public class ArrayParser implements Parser<JSONArray> {

	private JSONArray jsonArray;

	public ArrayParser() {
		init();
	}

	@Override
	public JSONArray commit() {
		return null;
	}

	@Override
	public boolean parse(char ch) {
		return false;
	}

	@Override
	public boolean isStart(char ch) {
		return false;
	}

	@Override
	public void init() {
		jsonArray.clear();
	}

	@Override
	public boolean isEnd() {
		return false;
	}
}
