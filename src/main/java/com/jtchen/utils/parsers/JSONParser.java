package com.jtchen.utils.parsers;

import com.jtchen.beans.JSONArray;
import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import java.util.Stack;

import static com.jtchen.utils.parsers.JSONParser.Brackets.LEFT_BRACKETS;
import static com.jtchen.utils.parsers.JSONParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 10:57
 */
@SuppressWarnings("StatementWithEmptyBody")
public class JSONParser implements Parser<JSON> {

	private final JSONFactory factory;
	private final BooleanParser booleanParser = new BooleanParser();
	private final NullParser nullParser = new NullParser();
	private final NumberParser numberParser = new NumberParser();
	private final StringParser stringParser = new StringParser();
	private final JSONArrayParser arrayParser = new JSONArrayParser();

	private State state;
	private final Stack<Object> stack = new Stack<>();


	@SuppressWarnings("unused")
	public JSONParser() {
		factory = LinkedJSONFactory.getInstance();
		init();
	}

	public JSONParser(JSONFactory factory) {
		this.factory = factory;
		init();
	}

	@Override
	public JSON commit() {
		if (!isEnd()) throw new JSONException("the json sentences is not perfect");
		JSON result = (JSON) stack.pop();

		init();

		return result;
	}


	private boolean isSpace(char ch) {
		return ch == ' ' || ch == '\n' || ch == '\t';
	}

	@Override
	public boolean parse(char ch) {
		switch (state) {
			case START:
				if (isSpace(ch)) {

				} else if (ch == '{') {
					stack.push(LEFT_BRACKETS);
					state = LEFT;
				} else return false;
				break;
			case END:
				if (isSpace(ch)) {
				} else if (ch == '}') {
					popToJSON();
				} else if (ch == ',') {
					state = POINT;
				}
				break;
			case LEFT:
				if (stringParser.isStart(ch)) {
					stringParser.parse(ch);
					state = STR_FRONT;
				} else if (isSpace(ch)) {

				} else if (ch == '}') {
					state = END;
					if (!stack.isEmpty() && stack.peek() == LEFT_BRACKETS) {
						if (stack.isEmpty()) return false;
						stack.pop();
						stack.push(factory.createJSON());
					} else return false;
				} else return false;
				break;
			case NUM:
				if (numberParser.parse(ch)) {

				} else if (ch == ',' || isSpace(ch) || ch == '}') {
					Double commit = numberParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case STR:
				if (stringParser.parse(ch)) {

				} else if (ch == ',' || isSpace(ch) || ch == '}') {
					String commit = stringParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case ARRAY:
				if (arrayParser.parse(ch)) {

				} else if (ch == ',' || isSpace(ch) || ch == '}') {
					JSONArray commit = arrayParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case BOOL:
				if (booleanParser.parse(ch)) {

				} else if (ch == ',' || isSpace(ch) || ch == '}') {
					Boolean commit = booleanParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case NULL:
				if (nullParser.parse(ch)) {

				} else if (ch == ',' || isSpace(ch) || ch == '}') {
					Object commit = nullParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case COLON:
				if (isSpace(ch)) {

				} else if (ch == '{') {
					stack.push(LEFT_BRACKETS);
					state = LEFT;
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
				} else if (arrayParser.isStart(ch)) {
					arrayParser.parse(ch);
					state = ARRAY;
				} else return false;
				break;
			case STR_FRONT:
				if (stringParser.parse(ch)) {

				} else if (isSpace(ch) || ch == ':') {
					String commit = stringParser.commit();
					stack.push(commit);

					if (isSpace(ch)) {
						state = SPACE_AFTER_STR_FRONT;
					} else {
						state = COLON;
					}
				} else return false;
				break;
			case SPACE_BEFORE_COMMA:
				if (isSpace(ch)) {

				} else if (ch == '}') {
					popToJSON();
				} else if (ch == ',') {
					state = POINT;
				} else return false;
				break;

			case SPACE_AFTER_STR_FRONT:
				if (isSpace(ch)) {

				} else if (ch == ':') {
					state = COLON;
				} else return false;
				break;

			case POINT:
				if (isSpace(ch)) {
				} else if (ch == '"') {
					state = STR_FRONT;
					stringParser.parse(ch);
				} else return false;
				break;
		}
		return true;
	}

	private void popToJSON() {
		JSON json = factory.createJSON();
		while (stack.peek() != LEFT_BRACKETS) {
			Object value = stack.pop();
			String key = (String) stack.pop();
			json.put(key, value);
		}

		stack.pop();
		stack.push(json);
		state = END;
	}

	private void putInJSON(char ch, Object commit) {
		stack.push(commit);


		if (ch == ',') {
			state = POINT;
		} else if (isSpace(ch)) {
			state = SPACE_BEFORE_COMMA;
		} else {
			popToJSON();
		}
	}


	@Override
	public boolean isStart(char ch) {
		return ch == '{';
	}

	@Override
	public void init() {
		stack.clear();
		state = START;

	}

	@Override
	public boolean isEnd() {
		return state == END && stack.size() == 1;
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
		ARRAY,
		POINT,
		END
	}

	enum Brackets {
		LEFT_BRACKETS
	}

}
