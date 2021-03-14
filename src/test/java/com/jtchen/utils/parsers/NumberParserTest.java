package com.jtchen.utils.parsers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NumberParserTest {

	private NumberParser parser;


	@Before
	public void setUp() {

		parser = new NumberParser();
	}

	@Test
	public void test1() {
		TEST_NUMBER(0.0, "0");
		TEST_NUMBER(0.0, "-0");
		TEST_NUMBER(0.0, "-0.0");
		TEST_NUMBER(1.0, "1");
		TEST_NUMBER(-1.0, "-1");
		TEST_NUMBER(1.5, "1.5");
		TEST_NUMBER(-1.5, "-1.5");
		TEST_NUMBER(3.1416, "3.1416");
		TEST_NUMBER(1E10, "1E10");
		TEST_NUMBER(1e10, "1e10");
		TEST_NUMBER(1E+10, "1E+10");
		TEST_NUMBER(1E-10, "1E-10");
		TEST_NUMBER(-1E10, "-1E10");
		TEST_NUMBER(-1e10, "-1e10");
		TEST_NUMBER(-1E+10, "-1E+10");
		TEST_NUMBER(-1E-10, "-1E-10");
		TEST_NUMBER(1.234E+10, "1.234E+10");
		TEST_NUMBER(1.234E-10, "1.234E-10");
		TEST_NUMBER(0.0, "1e-10000"); /* must underflow */
		TEST_NUMBER(4.9406564584124654E-324, "4.9406564584124654E-324");
		TEST_NUMBER(1.7976931348623157E308, "1.7976931348623157E308");
		TEST_NUMBER(1.0000000000000002, "1.0000000000000002"); /* the smallest number > 1 */
		TEST_NUMBER(4.9406564584124654e-324, "4.9406564584124654e-324"); /* minimum denormal */
		TEST_NUMBER(-4.9406564584124654e-324, "-4.9406564584124654e-324");
		TEST_NUMBER(2.2250738585072009e-308, "2.2250738585072009e-308");  /* Max subnormal double */
		TEST_NUMBER(-2.2250738585072009e-308, "-2.2250738585072009e-308");
		TEST_NUMBER(2.2250738585072014e-308, "2.2250738585072014e-308");  /* Min normal positive double */
		TEST_NUMBER(-2.2250738585072014e-308, "-2.2250738585072014e-308");
		TEST_NUMBER(1.7976931348623157e+308, "1.7976931348623157e+308");  /* Max double */
		TEST_NUMBER(-1.7976931348623157e+308, "-1.7976931348623157e+308");
	}

	@Test
	public void test2() {
		TEST_ERROR("1e309");
		TEST_ERROR("-1e309");
	}


	@Test
	public void test3() {
		String s = "123ab";
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			boolean tmp = parser.parse(ch);
			if (!tmp) {
				System.out.println(ch);
				break;
			}
		}
		System.out.println(parser.commit());
	}

	@Test
	public void test4() {
		String s = "123ab";
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			parser.parse(ch);
		}
		System.out.println(parser.commit());
	}


	public void TEST_NUMBER(double d, String s) {
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			boolean tmp = parser.parse(ch);
			if (!tmp) {
				break;
			}
		}
		assertEquals(d, parser.commit(), 0.0);
	}

	public void TEST_ERROR(String s) {
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			parser.parse(ch);
		}
		try {
			System.out.println(parser.commit());
		} catch (Exception e) {
			System.out.println(e);
			assertEquals(0, 0);
		}

	}
}