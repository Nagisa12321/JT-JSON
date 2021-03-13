package com.jtchen.utils.parsers;

import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import static com.jtchen.utils.parsers.BooleanParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 14:37
 */
public class BooleanParser implements Parser<Boolean> {

	private StringBuilder builder;
	private State state;

	public BooleanParser() {
		init();
	}

	@Override
	public Boolean commit() {
		String result = builder.toString();
		boolean isEnd = isEnd();
		State tmp = state;

		init();
		if (!isEnd) throw new JSONException("illegal string: " + result);
		else return tmp == TRUE_E;
	}

	@Override
	public boolean parse(char ch) {
		builder.append(ch);
		switch (state) {
			case START:
				if (ch == 'f') {
					state = FALSE_F;
				} else if (ch == 't') {
					state = TRUE_T;
				} else return false;
				break;
			case TRUE_E:
			case FALSE_E:
				return false;
			case TRUE_R:
				if (ch == 'u') {
					state = TRUE_U;
				} else return false;
				break;
			case TRUE_T:
				if (ch == 'r') {
					state = TRUE_R;
				} else return false;
				break;
			case TRUE_U:
				if (ch == 'e') {
					state = TRUE_E;
				} else return false;
				break;
			case FALSE_A:
				if (ch == 'l') {
					state = FALSE_L;
				} else return false;
				break;
			case FALSE_F:
				if (ch == 'a') {
					state = FALSE_A;
				} else return false;
				break;
			case FALSE_L:
				if (ch == 's') {
					state = FALSE_S;
				} else return false;
				break;
			case FALSE_S:
				if (ch == 'e') {
					state = FALSE_E;
				} else return false;
				break;
		}

		return true;
	}

	@Override
	public boolean isStart(char ch) {
		return ch == 't' || ch == 'f';
	}

	@Override
	public void init() {
		builder = new StringBuilder();
		state = START;
	}

	@Override
	public boolean isEnd() {
		return state == TRUE_E || state == FALSE_E;
	}

	enum State {
		START,
		TRUE_T,
		TRUE_R,
		TRUE_U,
		TRUE_E,
		FALSE_F,
		FALSE_A,
		FALSE_L,
		FALSE_S,
		FALSE_E
	}
}
