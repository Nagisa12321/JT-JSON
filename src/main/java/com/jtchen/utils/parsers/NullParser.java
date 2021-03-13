package com.jtchen.utils.parsers;

import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import static com.jtchen.utils.parsers.NullParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 15:35
 */
public class NullParser implements Parser<Object> {

	private StringBuilder builder;
	private State state;

	public NullParser() {
		init();
	}

	@Override
	public Object commit() {


		String result = builder.toString();
		boolean isEnd = isEnd();
		if (!isEnd) throw new JSONException("illegal string: " + result);

		return null;
	}

	@Override
	public boolean parse(char ch) {
		builder.append(ch);
		switch (state) {
			case START:
				if (ch == 'n') {
					state = N;
				} else return false;
				break;
			case N:
				if (ch == 'u') {
					state = U;
				} else return false;
				break;
			case U:
				if (ch == 'l') {
					state = L_1;
				} else return false;
				break;
			case L_1:
				if (ch == 'l') {
					state = L_2;
				} else return false;
				break;
			case L_2:
				return false;
		}
		return true;
	}

	@Override
	public boolean isStart(char ch) {
		return ch == 'n';
	}

	@Override
	public void init() {
		builder = new StringBuilder();
		state = START;
	}

	@Override
	public boolean isEnd() {
		return state == L_2;
	}

	enum State {
		START,
		N,
		U,
		L_1,
		L_2
	}
}
