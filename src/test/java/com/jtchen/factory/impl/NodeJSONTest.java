package com.jtchen.factory.impl;

import com.jtchen.json.impl.LinkedJSON;
import org.junit.Before;
import org.junit.Test;

public class NodeJSONTest {

	private LinkedJSON nj;

	@Before
	public void setUp() {

		nj = new LinkedJSON();
	}

	@Test
	public void test1() {
		nj.put("name", "jtchen");
		nj.put("age", 18);


		System.out.println(nj.toString());
	}

	@Test
	public void test2() {
		LinkedJSON format = new LinkedJSON();

		format.put("type", "rect");
		format.put("width", 1920);
		format.put("height", 1080);
		format.put("interlace", false);
		format.put("frame rate", 24);

		LinkedJSON js = new LinkedJSON();

		js.put("name", "Jack");
		js.put("format", format);


		int[][] ints = {{1}, {2, 3}, {3, 4, 5}};
		js.put("arr", ints);

		System.out.println(js);
	}
}