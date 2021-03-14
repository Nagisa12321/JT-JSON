package com.jtchen.utils;

import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.utils.parsers.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class JSONParserTest {


	@Test
	public void test3() {
		JSONFactory linkedJSONFactory = LinkedJSONFactory.getInstance();
		JSON json = linkedJSONFactory.parse("{\n" +
				"  \"name\": \"Jack (Bee) \tNimble\",\n" +
				"  \"format\": {\n" +
				"    \"type\": \"rect\",\n" +
				"    \"width\": 1920,\n" +
				"    \"height\": 1080,\n" +
				"    \"interlace\": false,\n" +
				"    \"frame rate\": 24\n" +
				"  },\n" +
				"  \"arr\": [1, 2, 3]\n" +
				"}\n" +
				"\n" +
				"\n");
		String string = json.toString();
		JSON json1 = linkedJSONFactory.parse(string);

		System.out.println(json);
		System.out.println(json1);
	}

}