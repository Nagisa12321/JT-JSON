package com.jtchen.utils;

import com.jtchen.beans.Pair;
import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.utils.exception.JSONException;
import com.jtchen.utils.parsers.BooleanParser;
import com.jtchen.utils.parsers.NullParser;
import com.jtchen.utils.parsers.NumberParser;
import com.jtchen.utils.parsers.StringParser;

import static com.jtchen.utils.JSONParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 10:57
 */
public class JSONParser implements Parser<JSON> {

	private final JSONFactory factory;
	private final BooleanParser booleanParser = new BooleanParser();
	private final NullParser nullParser = new NullParser();
	private final NumberParser numberParser = new NumberParser();
	private final StringParser stringParser = new StringParser();

	private JSON json;
	private State state;
	private Pair<String, Object> keyAndValue;


	public JSONParser() {
		factory = new LinkedJSONFactory();
		init();
	}

	public JSONParser(JSONFactory factory) {
		this.factory = factory;
		init();
	}

	@Override
	public JSON commit() {
		boolean isEnd = isEnd();
		JSON result = this.json;

		init();
		if (!isEnd) throw new JSONException("the json sentences is not perfect");

		return result;
	}

	@Override
	public boolean parse(char ch) {
		switch (state) {
			case START:
				if (ch == ' ' || ch == '\n' || ch == '\t') {

				} else if (ch == '{') {
					state = LEFT;
				} else return false;
				break;
			case END:
				if (ch != ' ' && ch != '\n' && ch != '\t')
					return false;
				break;
			case LEFT:
				if (stringParser.isStart(ch)) {
					stringParser.parse(ch);
					state = STR_FRONT;
				} else if (ch == ' ' || ch == '\n' || ch == '\t') {

				} else if (ch == '}') {
					state = END;
				} else return false;
				break;
			case NUM:
				if (numberParser.parse(ch)) {

				} else if (ch == ',' || ch == ' ' || ch == '}' || ch == '\n' || ch == '\t') {
					Double commit = numberParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case STR:
				if (stringParser.parse(ch)) {

				} else if (ch == ',' || ch == ' ' || ch == '}' || ch == '\n' || ch == '\t') {
					String commit = stringParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;

			case BOOL:
				if (booleanParser.parse(ch)) {

				} else if (ch == ',' || ch == ' ' || ch == '}' || ch == '\n' || ch == '\t') {
					Boolean commit = booleanParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case NULL:
				if (nullParser.parse(ch)) {

				} else if (ch == ',' || ch == ' ' || ch == '}' || ch == '\n' || ch == '\t') {
					Object commit = nullParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case COLON:
				if (ch == ' ' || ch == '\n' || ch == '\t') {

				} else if (numberParser.isStart(ch)) {
					numberParser.parse(ch);
					state = NUM;
				} else if (stringParser.isStart(ch)) {
					stringParser.parse(ch);
					state = STR;
				} else if (booleanParser.isStart(ch)) {
					booleanParser.parse(ch);
					state = BOOL;
				} else if (nullParser.isStart(ch)) {
					nullParser.parse(ch);
					state = NULL;
				} else return false;
				break;
			case STR_FRONT:
				if (stringParser.parse(ch)) {

				} else if (ch == ' ' || ch == ':' || ch == '\n' || ch == '\t') {
					String commit = stringParser.commit();
					keyAndValue.setKey(commit);

					if (ch == ' ' || ch == '\n' || ch == '\t') {
						state = SPACE_AFTER_STR_FRONT;
					} else {
						state = COLON;
					}
				} else return false;
				break;
			case SPACE_BEFORE_COMMA:
				if (ch == ' ' || ch == '\n' || ch == '\t') {

				} else if (ch == '}') {
					state = END;
				} else if (ch == ',') {
					state = LEFT;
				} else return false;
				break;

			case SPACE_AFTER_STR_FRONT:
				if (ch == ' ' || ch == '\n' || ch == '\t') {

				} else if (ch == ':') {
					state = COLON;
				} else return false;
				break;
		}
		return true;
	}

	private void putInJSON(char ch, Object commit) {
		keyAndValue.setValue(commit);

		json.put(keyAndValue.getKey(), keyAndValue.getValue());
		keyAndValue.init();


		if (ch == ',') {
			state = LEFT;
		} else if (ch == ' ' || ch == '\n' || ch == '\t') {
			state = SPACE_BEFORE_COMMA;
		} else {
			state = END;
		}
	}


	@Override
	public boolean isStart(char ch) {
		return ch == '{';
	}

	@Override
	public void init() {
		json = factory.createJSON();
		state = START;
		keyAndValue = new Pair();

	}

	@Override
	public boolean isEnd() {
		return state == END;
	}

	/*

	leptjson 是一个手写的递归下降解析器（recursive descent parser）。
	由于 JSON 语法特别简单，我们不需要写分词器（tokenizer），只需检测下一个字符，
	便可以知道它是哪种类型的值，然后调用相关的分析函数。对于完整的 JSON 语法，跳过空白后，只需检测当前字符：


	n 		--> null
	t 		--> true
	f 		--> false
	" 		--> String
	0-9/- 	--> number
	[		--> array
	{		--> JSON

	 */

	public JSON parse(String s) {
		char[] chs = s.toCharArray();

		for (char ch : chs) {
			if (!parse(ch)) throw new JSONException("illegal character: " + ch);
		}
		return commit();
	}


	enum State {
		START,
		LEFT,
		STR_FRONT,
		SPACE_AFTER_STR_FRONT,
		COLON,
		NUM,
		STR,
		BOOL,
		NULL,
		SPACE_BEFORE_COMMA,
		END
	}

}
