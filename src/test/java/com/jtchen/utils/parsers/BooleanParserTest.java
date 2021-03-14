package com.jtchen.utils.parsers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanParserTest {


	private BooleanParser parser;

	@Before
	public void start() {
		parser = new BooleanParser();
	}

	@Test
	public void test1() {
		 TEST_BOOLEAN(true, "true");
		 TEST_BOOLEAN(false, "false");
		TEST_BOOLEAN(true, "true");
		 TEST_BOOLEAN(true, "true");
	}

	public void TEST_BOOLEAN(boolean b, String bool) {
		char[] chs = bool.toCharArray();
		for (char c : chs) {
			parser.parse(c);
		}
		Boolean commit = parser.commit();
		System.out.println(commit);
		assertEquals(b, commit);
	}

}