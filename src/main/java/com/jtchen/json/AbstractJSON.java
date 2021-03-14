package com.jtchen.json;

import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 19:36
 */
public abstract class AbstractJSON implements JSON {

	private final JSONFactory parser = LinkedJSONFactory.getInstance();

	public String toString() {
		return parser.unformattedParse(this);
	}


	// ������ܻ�ʵ��JSON�ӿڶ���ķ���
	// ....
}
