package com.jtchen.utils.parsers;

import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import static com.jtchen.utils.parsers.NumberParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 15:08
 */
public class NumberParser implements Parser<Double> {

	private StringBuilder builder;
	private State state;

	public NumberParser() {
		init();
	}

	public void init() {
		builder = new StringBuilder();
		state = START;
	}

	@Override
	public boolean isEnd() {
		return state == NUMBER || state == ZERO || state == NUM_AFTER_E || state == NUM_AFTER_POINT;
	}


	@Override
	public Double commit() {
		String tmp = builder.toString();
		double result = Double.parseDouble(tmp);
		boolean isEnd = isEnd();
		// 初始化
		init();
		if (result == Double.NEGATIVE_INFINITY
				|| result == Double.POSITIVE_INFINITY) {
			throw new JSONException("double is out of range: " + tmp);
		} else if (!isEnd) throw new JSONException("double is not closed: " + tmp);

		return result;
	}

	// 解析一个char并改变状态
	@Override
	public boolean parse(char ch) {
		switch (state) {
			case E:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
					state = NUM_AFTER_E;
				} else if (ch == '+' || ch == '-') {
					builder.append(ch);
					state = OPERATOR_AFTER_E;
				} else return false;
				break;
			case ZERO:
				if (ch == 'e' || ch == 'E') {
					builder.append(ch);
					state = E;
				} else if (ch == '.') {
					builder.append(ch);
					state = POINT;
				} else return false;
				break;
			case POINT:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
					state = NUM_AFTER_POINT;
				} else return false;
				break;
			case START:
				if (ch == '-') {
					builder.append(ch);
					state = NEGATIVE;
				} else if (ch >= '1' && ch <= '9') {
					builder.append(ch);
					state = NUMBER;
				} else if (ch == '0') {
					builder.append(ch);
					state = ZERO;
				} else return false;
				break;
			case NUMBER:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
				} else if (ch == '.') {
					builder.append(ch);
					state = POINT;
				} else if (ch == 'e' || ch == 'E') {
					builder.append(ch);
					state = E;
				} else return false;
				break;
			case NEGATIVE:
				if (ch == '0') {
					builder.append(ch);
					state = ZERO;
				} else if (ch >= '1' && ch <= '9') {
					builder.append(ch);
					state = NUMBER;
				} else return false;
				break;
			case NUM_AFTER_E:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
				} else return false;
				break;
			case NUM_AFTER_POINT:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
				} else if (ch == 'e' || ch == 'E') {
					builder.append(ch);
					state = E;
				} else return false;
				break;
			case OPERATOR_AFTER_E:
				if (ch >= '0' && ch <= '9') {
					builder.append(ch);
					state = NUM_AFTER_E;
				} else return false;
				break;
		}

		return true;
	}


	// 检测是否为数字的开头
	@Override
	public boolean isStart(char ch) {
		return (ch >= '0' && ch <= '9') || ch == '-';
	}


	enum State {
		START, NUMBER, NEGATIVE, ZERO, POINT, NUM_AFTER_POINT, E, NUM_AFTER_E, OPERATOR_AFTER_E
	}
}
