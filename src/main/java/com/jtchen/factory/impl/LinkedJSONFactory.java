package com.jtchen.factory.impl;

import com.jtchen.factory.JSONFactory;
import com.jtchen.json.JSON;
import com.jtchen.json.impl.LinkedJSON;
import com.jtchen.utils.exception.JSONException;
import com.jtchen.utils.parsers.JSONParser;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/11 12:01
 */
public class LinkedJSONFactory extends JSONFactory {

	// ���õ���ģʽ: ��Ϊ������ֻ��Ҫһ������
	private static final LinkedJSONFactory factory = new LinkedJSONFactory();

	public static JSONFactory getInstance() {
		return factory;
	}

	private LinkedJSONFactory() {}

	/**
	 * ר������LinkedJSON�Ĺ���, �ù����̳�JSONFactory,
	 * ר�Ÿ��߸���Ӧ������LinkedJSON
	 *
	 * @param s �ַ���
	 * @return ����������JSONʵ��
	 */
	@Override
	public JSON parse(String s) {
		// ��JSON��������ע����Ӧ�Ĺ���, ����JSON������ί������
		/*

		Ϊʲô����JSON������ֱ������?

		 - JSON�����������˺ܶ��������, ���Ҽ̳���Parser�ӿ�, �����ž���һ��������
			ֻ��������Ľ����������Ĳ�ͬ, ����������JSON������ѡ�
			����ܶ�Ľ���������Ӧ�ö��û���¶, �û�ֻ��Ҫһ��parse()��������

		������JSONParser�Ľ����������н���,
		JSONParser�Ľ�����������Ӧ�ĵ���������������н���
		 */
		JSONParser jsonParser = new JSONParser(this);
		char[] chs = s.toCharArray();
		for (char ch : chs) {
			if (!jsonParser.parse(ch)) throw new JSONException("illegal character: " + ch);
		}
		return jsonParser.commit();
	}


	// ����һ���յ�JSON����
	@Override
	public JSON createJSON() {
		return new LinkedJSON();
	}
}
