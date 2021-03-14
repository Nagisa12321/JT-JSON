import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtchen.json.JSON;
import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.factory.JSONFactory;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertNull;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/12 14:23
 */
public class JSONTest {


	private String jsonStr;
	private final static Logger logger = Logger.getLogger(JSONTest.class);

	@Before
	public void getFile() throws Exception {
		FileInputStream fis = new FileInputStream("src/main/resources/json1.json");
		StringBuilder builder = new StringBuilder();

		int data;
		while ((data = fis.read()) != -1) {
			builder.append((char) (data));
		}

		jsonStr = builder.toString();
		// logger.info("获得字符串: \n" + jsonStr);
	}

	@Test
	public void testInitialization() {
		JSONFactory factory = new LinkedJSONFactory();

		// 通过工厂产生一个json对象
		JSON json = factory.parse(jsonStr);

		System.out.println(json);
	}


	// 测试对于空值的解析
	@Test
	public void testParseNull() {
		String s = "{ \"a\" : null  }";

		JSONFactory factory = new LinkedJSONFactory();

		// 通过工厂产生一个json对象
		JSON json = factory.parse(s);

		Object a = json.get("a");

		logger.info("a: " + a);
		assertNull(a);

	}


	@Test
	public void testAlibabaJSON() {
		String s = "{\"name\":123}";
		JSONObject parse = (JSONObject) com.alibaba.fastjson.JSON.parse(s);

		System.out.println(parse);

		LinkedJSONFactory factory = new LinkedJSONFactory();
		JSON parse1 = factory.parse(s);
		System.out.println(parse1);


	}

}
