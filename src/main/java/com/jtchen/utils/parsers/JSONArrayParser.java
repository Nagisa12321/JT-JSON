package com.jtchen.utils.parsers;

import com.jtchen.beans.JSONArray;
import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import java.util.Stack;

import static com.jtchen.utils.parsers.JSONArrayParser.State.*;
import static com.jtchen.utils.parsers.JSONParser.Brackets.LEFT_BRACKETS;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 17:59
 */
@SuppressWarnings("StatementWithEmptyBody")
public class JSONArrayParser implements Parser<JSONArray> {
	private final BooleanParser booleanParser = new BooleanParser();
	private final NullParser nullParser = new NullParser();
	private final NumberParser numberParser = new NumberParser();
	private final StringParser stringParser = new StringParser();
	private State state;
	private final Stack<Object> stack;

	public JSONArrayParser() {
		stack = new Stack<>();
		state = START;
	}

	@Override
	public JSONArray commit() {
		if (!isEnd()) throw new JSONException("Illegal array style.");

		JSONArray result = (JSONArray) stack.pop();

		init();
		reserveArray(result);
		return result;
	}

	private void reserveArray(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.size() / 2; i++) {
			Object tmp = jsonArray.get(i);
			jsonArray.set(i, jsonArray.get(jsonArray.size() - 1 - i));
			jsonArray.set(jsonArray.size() - 1 - i, tmp);
		}
	}

	private boolean isSpace(char ch) {
		return ch == ' ' || ch == '\n' || ch == '\t';
	}

	@Override
	public boolean parse(char ch) {
		switch (state) {
			case RIGHT:
				if (isSpace(ch)) {
				} else if (ch == ']') {
					JSONArray jsonArray = new JSONArray();
					while (!stack.isEmpty() && stack.peek() != LEFT_BRACKETS) {
						jsonArray.add(stack.pop());
					}
					if (stack.isEmpty()) return false;
					stack.pop();
					stack.push(jsonArray);
				} else if (ch == ',') {
					if (stack.size() == 1) return false;
					state = POINT;
				} else return false;
				break;
			case START:
				if (isSpace(ch)) {
				} else if (isStart(ch)) {
					stack.push(LEFT_BRACKETS);
					state = LEFT;
				} else return false;
				break;
			case LEFT:
				if (isSpace(ch)) {
				} else if (ch == '[')
					stack.push(LEFT_BRACKETS);

					// 标记点: 可能出错
				else if (ch == ']') {
					state = RIGHT;
					if (stack.peek() == LEFT_BRACKETS) {
						stack.pop();
						stack.push(new JSONArray());
					} else return false;
				} else if (stringParser.isStart(ch)) {
					state = STR;
					stringParser.parse(ch);
				} else if (numberParser.isStart(ch)) {
					state = NUM;
					numberParser.parse(ch);
				} else if (nullParser.isStart(ch)) {
					state = NULL;
					nullParser.parse(ch);
				} else if (booleanParser.isStart(ch)) {
					state = BOOLEAN;
					booleanParser.parse(ch);
				} else return false;
				break;

			case POINT:
				if (isSpace(ch)) {
					state = AFTER;
				} else {
					if (parseChar(ch)) return false;
				}
				break;
			case AFTER:
				if (isSpace(ch)) {
				} else if (parseChar(ch)) return false;
				break;
			case BEFORE:
				if (isSpace(ch)) {
				} else if (ch == ',') {
					state = POINT;
				} else if (ch == ']') {
					state = RIGHT;

					JSONArray jsonArray = new JSONArray();
					while (stack.peek() != LEFT_BRACKETS) {
						jsonArray.add(stack.pop());
					}
					stack.pop();
					reserveArray(jsonArray);
					stack.push(jsonArray);
				} else return false;
				break;
			case BOOLEAN:
				if (booleanParser.parse(ch)) {
				} else if (isSpace(ch) || ch == ']' || ch == ',') {
					Boolean commit = booleanParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case NUM:
				if (numberParser.parse(ch)) {
				} else if (isSpace(ch) || ch == ']' || ch == ',') {
					Double commit = numberParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case STR:
				if (stringParser.parse(ch)) {
				} else if (isSpace(ch) || ch == ']' || ch == ',') {
					String commit = stringParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
			case NULL:
				if (nullParser.parse(ch)) {
				} else if (isSpace(ch) || ch == ']' || ch == ',') {
					Object commit = nullParser.commit();
					putInJSON(ch, commit);
				} else return false;
				break;
		}
		return true;
	}

	private boolean parseChar(char ch) {
		if (stringParser.isStart(ch)) {
			state = STR;
			stringParser.parse(ch);
		} else if (numberParser.isStart(ch)) {
			state = NUM;
			numberParser.parse(ch);
		} else if (nullParser.isStart(ch)) {
			state = NULL;
			numberParser.parse(ch);
		} else if (booleanParser.isStart(ch)) {
			state = BOOLEAN;
			booleanParser.parse(ch);
		} else if (ch == '[') {
			stack.push(LEFT_BRACKETS);
			state = LEFT;
		} else return true;
		return false;
	}

	private void putInJSON(char ch, Object commit) {
		stack.push(commit);

		if (isSpace(ch)) {
			state = BEFORE;
		} else if (ch == ']') {
			state = RIGHT;

			JSONArray jsonArray = new JSONArray();
			while (stack.peek() != LEFT_BRACKETS) {
				jsonArray.add(stack.pop());
			}
			stack.pop();
			if (!stack.isEmpty()) reserveArray(jsonArray);
			stack.push(jsonArray);
		} else {
			state = POINT;
		}
	}


	@Override
	public boolean isStart(char ch) {
		return ch == '[';
	}

	@Override
	public void init() {
		stack.clear();
		state = START;
	}

	@Override
	public boolean isEnd() {
		return stack.size() == 1 && state == RIGHT;
	}


	enum State {
		START,
		LEFT,
		RIGHT,
		BEFORE,
		AFTER,
		STR,
		NUM,
		NULL,
		BOOLEAN,
		POINT
	}

}
