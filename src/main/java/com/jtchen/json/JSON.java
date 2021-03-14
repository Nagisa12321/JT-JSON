package com.jtchen.json;

import java.util.Map;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 10:34
 */
public interface JSON extends Map<String, Object> {

	// 声明JSON对象是一个类似MAP的数据结构
	// 日后可能会添加方法
	// 如getIntegerByKey(String key) ... etc.
}
