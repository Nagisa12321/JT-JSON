package com.jtchen.utils.parsers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringParserTest {


	// String ×ªÒå×Ö·û²âÊÔ1
	@Test
	public void test1() {
		String testStr1 = "1\23\t234\1244123123145\u1222\2\1\1asd";
		String testStr2 = "abc\"defg";
		String testStr3 = "123\t234";
		String testStr4 = "123\u12344";
		String testStr5 = "123\\\0ty";

		System.out.println(testStr1);
		System.out.println(testStr2);
		System.out.println(testStr3);
		System.out.println(testStr4);
		System.out.println(testStr5);
	}

	// String ×ªÒå×Ö·û²âÊÔ2
	@Test
	public void test2() {
		StringBuilder builder = new StringBuilder();
		builder.append('\\');
		builder.append('u');
		builder.append('1');
		builder.append('2');
		builder.append('3');
		builder.append('4');


		System.out.println('\u1234');// ?

	}

	// String ×ªÒå×Ö·û²âÊÔ3
	@Test
	public void test4() {
//		String s = "{ \"abc\" : \"de\"f\" }";
//		System.out.println(s);
//		char[] chs = s.toCharArray();
//		System.out.println(Arrays.toString(chs));

		JSONObject parse = (JSONObject) JSON.parse("{ \"abc\" : \"test\n\" }");
		System.out.println(parse);
		System.out.println(parse.getString("abc"));
	}


	@Test
	public void testParse() {
		TEST_SAME_AS_ALIBABA_JSON("\"hello world\"");
		TEST_SAME_AS_ALIBABA_JSON("\"just a test\"");
		TEST_SAME_AS_ALIBABA_JSON("\"test\n\"");
		TEST_SAME_AS_ALIBABA_JSON("\"test\\\"");
		TEST_SAME_AS_ALIBABA_JSON("\"\"");
	}

	@Test
	public void test5() {

		String s = "\"test\\\"";
		StringParser parser = new StringParser();

		String result = null;
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			boolean tmp = parser.parse(ch);
			if (!tmp) {
				result = parser.commit();
				break;
			}
		}
		if (result == null) result = parser.commit();


		System.out.println(result);
	}

	@Test
	public void test6() {
		TEST_STRING("", "\"\"");
		TEST_STRING("Hello", "\"Hello\"");
		TEST_STRING("Hello\nWorld", "\"Hello\\nWorld\"");
		TEST_STRING("\" \\ / \b \f \n \r \t", "\"\\\" \\\\ \\/ \\b \\f \\n \\r \\t\"");
		TEST_STRING("\" \\ /", "\"\\\" \\\\ \\/\"");
		TEST_STRING("\b \f \n \r \t", "\"\\b \\f \\n \\r \\t\"");
	}


	public void TEST_STRING(String trueAnswer, String s) {
		String result = null;

		StringParser parser = new StringParser();
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			boolean tmp = parser.parse(ch);
			if (!tmp) {
				result = parser.commit();
				break;
			}
		}

		if (result == null) result = parser.commit();

		System.out.println(trueAnswer);
		System.out.println(result);
		assertEquals(trueAnswer, result);

	}


	public void TEST_SAME_AS_ALIBABA_JSON(String s) {
		JSONObject json = (JSONObject) JSON.parse("{ \"name\" : " + s + "}");
		String result = null;

		StringParser parser = new StringParser();
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			boolean tmp = parser.parse(ch);
			if (!tmp) {
				result = parser.commit();
				break;
			}
		}

		if (result == null) result = parser.commit();


		System.out.println("result: " + result + ", alibaba result: " + json);
	}
}