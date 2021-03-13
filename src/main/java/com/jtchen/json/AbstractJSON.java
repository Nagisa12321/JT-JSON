package com.jtchen.json;

import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 19:36
 */
public abstract class AbstractJSON implements JSON {

	private JSONFactory parser = new LinkedJSONFactory();

	/**
	 * ��������: ����json����, ���ַ���ת��ΪJSON����
	 *
	 * @param s ������json�ַ���
	 * @return JSON����
	 */
	public static JSON parse(String s) {
		return null;
	}

	public String toString() {
		return parser.unformattedParse(this);
	}



}
