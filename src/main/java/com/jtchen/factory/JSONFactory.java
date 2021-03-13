package com.jtchen.factory;

import com.jtchen.json.JSON;

import java.util.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/10 20:22
 */
public abstract class JSONFactory {

	public abstract JSON parse(String s);

	public List<JSON> parseJSONs(List<String> list) {
		ArrayList<JSON> jsons = new ArrayList<>();
		for (String s : list)
			jsons.add(parse(s));

		return jsons;
	}

	public JSON parse(Map<String, Object> map) {
		JSON json = createJSON();

		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			json.put(entry.getKey(), entry.getValue());
		}

		return json;
	}


	// 无格式解析json对象
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

	public abstract JSON createJSON();
}
