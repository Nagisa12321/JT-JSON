package com.jtchen.factory;

import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.json.JSON;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONFactoryTest {

	@Test
	public void test1() {
		String s = "{\n" +
				"  \"name\": \"Jack (\\\"Bee\\\") Nimble\",\n" +
				"  \"type\": \"rect\",\n" +
				"  \"width\": 1920,\n" +
				"  \"height\": 1080,\n" +
				"  \"interlace\": false,\n" +
				"  \"frame rate\": 24\n" +
				"}";



		JSONFactory jsonFactory = new LinkedJSONFactory();
		JSON parse = jsonFactory.parse(s);
		System.out.println(parse);
	}

}