package com.jtchen.utils.parsers;

import com.jtchen.beans.JSONArray;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONArrayParserTest {


	private JSONArrayParser parser;


	@Before
	public void setUp() throws Exception {
		parser = new JSONArrayParser();
	}


	@Test
	public void test1() {
		parseArray("[\n" +
				"  \"test String\",\n" +
				"  1,\n" +
				"  2,\n" +
				"  434,\n" +
				"  true,\n" +
				"  [\n" +
				"    1,\n" +
				"    2,\n" +
				"    3\n" +
				"  ],\n" +
				"  [\n" +
				"    [],\n" +
				"    2\n" +
				"  ]\n" +
				"]");

		parseArray("[ null , false , true , 123 , \"abc\" ]");
		parseArray("[1, 2, 3,[], 4, 5]");
		parseArray("[ [ ] , [ 0 ] , [ 0 , 1 ] , [ 0 , 1 , 2 ] ]");
		parseArray("[[],[[[]]]]");
		parseArray("[\"]\"]");
	}


	@Test
	public void test2() {
		parseArray("[1, 2, 4],");
	}

	public void parseArray(String s) {
		char[] chs = s.toCharArray();

		for (char ch : chs) {
			parser.parse(ch);
		}
		JSONArray commit = parser.commit();
		System.out.println(commit);
	}
}