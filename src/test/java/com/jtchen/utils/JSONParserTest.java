package com.jtchen.utils;

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
	public void test2() {
		parseJSON1("{\n" +
				"  \"name\": \"Jack (\\\"Bee\\\") \tNimble\",\n" +
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
	}

	@Test
	public void test3() {
		LinkedJSONFactory linkedJSONFactory = new LinkedJSONFactory();
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


	public void parseJSON1(String s) {
		try {
			JSON json = parser.parse(s);
			FileOutputStream fos = new FileOutputStream("src/main/resources/outputjson.json");

			Set<Map.Entry<String, Object>> set = json.entrySet();
			int idx = 0;
			for (Map.Entry<String, Object> entry : set) {
				if (idx == 0) fos.write('{');


				// --------- key ---------

				fos.write('\"');
				String key = entry.getKey();
				char[] chs = key.toCharArray();
				for (char ch : chs) {
					fos.write(ch);
				}
				fos.write('\"');
				fos.write(':');


				// --------- value ---------
				Object value = entry.getValue();
				// 若是字符串要加上引号
				if (value instanceof String) {
					fos.write('"');
					String val = (String) value;
					char[] chs1 = val.toCharArray();
					for (char ch : chs1) {
						fos.write(ch);
					}
					fos.write('"');
				}

					// 若是arr要调用deepToString() 方法
				else if (value.getClass().isArray()) {
					String arr = Arrays.deepToString((Object[]) value);
					char[] chs1 = arr.toCharArray();
					for (char ch : chs1) {
						fos.write(ch);
					}
				} else {
					String string = value.toString();
					char[] chs1 = string.toCharArray();
					for (char ch : chs1) {
						fos.write(ch);
					}
				}


				if (idx == set.size() - 1) fos.write('}');
				else fos.write(',');
				idx++;
			}

			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}