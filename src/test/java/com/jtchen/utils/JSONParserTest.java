package com.jtchen.utils;

import com.jtchen.json.JSON;
import org.junit.Before;
import org.junit.Test;

public class JSONParserTest {
	private JSONParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new JSONParser();
	}

	@Test
	public void test1() {
		String exp1 = "{\"name\":\"jthen\"}";
		String exp2 = "{\n" +
				"  \"type\": \"rect\",\n" +
				"  \"width\": 1920,\n" +
				"  \"height\": 1080,\n" +
				"  \"interlace\": false,\n" +
				"  \"frame rate\": 24\n" +
				"}\n" +
				"\n" +
				"\n";

		// JSON parse1 = parser.parse(exp1);
		JSON parse2 = parser.parse(exp2);


		// System.out.println(parse1);
		System.out.println(parse2);
	}

	@Test
	public void testMethod() {
	}
}