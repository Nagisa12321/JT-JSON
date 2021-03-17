package com.jtchen.utils.parsers;

import com.jtchen.utils.Parser;
import com.jtchen.utils.exception.JSONException;

import static com.jtchen.utils.parsers.StringParser.State.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/13 11:04
 */
public class StringParser implements Parser<String> {

	private StringBuilder builder;
	private State state;

	public StringParser() {
		init();
	}

	@Override
	public String commit() {
		String result = builder.toString();
		boolean isEnd = !isEnd();
		init();
		if (isEnd) throw new JSONException("String is not closed: " + result);
		else return result;
	}

	/*
	而对于 JSON字符串中的  是以 16 进制表示码点 U+0000 至 U+FFFF，我们需要：
		1.解析 4 位十六进制整数为码点；
		2.由于字符串是以 UTF-8 存储，我们要把这个码点编码成 UTF-8。
	 */
	@Override
	public boolean parse(char ch) {
		switch (state) {
			case START:
				if (ch == '"') {
					state = READING;
				} else return false;
				break;
			case END:
				return false;
			case READING:
				if (ch == '\\') {
					state = ESCAPE;
				} else if (ch == '"') {
					state = END;
				} else {
					builder.append(ch);
				}
				break;
			case ESCAPE:
				if (ch == '\\') {
					builder.append('\\');
					state = READING;
				} else if (ch == '/') {
					builder.append('/');
					state = READING;
				} else if (ch == 'b') {
					builder.append('\b');
					state = READING;
				} else if (ch == 'f') {
					builder.append('\f');
					state = READING;
				} else if (ch == 'n') {
					builder.append('\n');
					state = READING;
				} else if (ch == 'r') {
					builder.append('\r');
					state = READING;
				} else if (ch == 't') {
					builder.append('\t');
					state = READING;
				} else if (ch == '"') {
					builder.append('\"');
					state = READING;
				} else return false;

				break;
		}
		return true;
	}


	@Override
	public boolean isStart(char ch) {
		return ch == '"';
	}

	@Override
	public void init() {
		builder = new StringBuilder();
		state = START;
	}

	@Override
	public boolean isEnd() {
		return state == END;
	}

	/*
	string = quotation-mark *char quotation-mark
	char = unescaped /
   		escape (
       		%x22 /          ; "    quotation mark  U+0022
       		%x5C /          ; \    reverse solidus U+005C
       		%x2F /          ; /    solidus         U+002F
       		%x62 /          ; b    backspace       U+0008
       		%x66 /          ; f    form feed       U+000C
       		%x6E /          ; n    line feed       U+000A
       		%x72 /          ; r    carriage return U+000D
       		%x74 /          ; t    tab             U+0009
       		%x75 4HEXDIG )  ; uXXXX                U+XXXX
		escape = %x5C          ; \
		quotation-mark = %x22  ; "
		unescaped = %x20-21 / %x23-5B / %x5D-10FFFF
	 */


	enum State {
		START,
		READING,
		END,
		ESCAPE
	}
}
