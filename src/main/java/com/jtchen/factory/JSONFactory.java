package com.jtchen.factory;

import com.jtchen.json.JSON;

import java.util.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 20:22
 */
@SuppressWarnings("unused")
public abstract class JSONFactory {

	/**
	 * 通过字符串生产出具体JSON对象
	 *
	 * 抽象化原因: 我们不知道要生产出的是具体哪种JSON类型
	 * 可能是MAPJSON, 也有可能是LINKEDJSON, 以后可能还会新增新的JSON,
	 * 让子类决定我们应该产出什么JSON
	 *
	 * @param s 字符串
	 * @return 具体JSON对象
	 */
	public abstract JSON parse(String s);


	// 通过一串字符串, 生产出一串JSON
	public List<JSON> parseJSONs(List<String> list) {
		ArrayList<JSON> jsons = new ArrayList<>();
		for (String s : list)
			jsons.add(parse(s));

		return jsons;
	}

	// 通过一个MAP生产出JSON对象
	public JSON parse(Map<String, Object> map) {
		JSON json = createJSON();

		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			json.put(entry.getKey(), entry.getValue());
		}

		return json;
	}


	// 无格式解析json对象
	// 主要用于JSON.toString()方法
	public String unformattedParse(JSON json) {
		Set<Map.Entry<String, Object>> set = json.entrySet();
		StringBuilder builder = new StringBuilder();
		int idx = 0;
		for (Map.Entry<String, Object> entry : set) {
			if (idx == 0) builder.append('{');


			// --------- key ---------
			builder.append('\"')
					.append(entry.getKey())
					.append('\"')
					.append(':');


			// --------- value ---------
			Object value = entry.getValue();
			// 若是字符串要加上引号
			if (value instanceof String)
				builder.append('"').append(value).append('"');

				// 若是arr要调用deepToString() 方法
			else if (value.getClass().isArray()) {
				builder.append(Arrays.deepToString((Object[]) value));
			} else builder.append(value);


			if (idx == set.size() - 1) builder.append('}');
			else builder.append(',');
			idx++;
		}

		return builder.toString();
	}

	// 由子类决定生产出什么种类的JSON对象
	public abstract JSON createJSON();
}
