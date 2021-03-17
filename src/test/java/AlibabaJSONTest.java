import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;
import org.junit.Test;


/**
 * @author jtchen
 * @version 1.0
 * @date 2021/3/14 19:25
 */
public class AlibabaJSONTest {


	@Test
	public void test1() {
		String s = "{\n" +
				"  \"name\": \"Ja\\nck (\\\"Bee\\\") Nimble\",\n" +
				"  \"format\": {\n" +
				"    \"width\": 1920.0,\n" +
				"    \"frame rate\": 24.0,\n" +
				"    \"type\": \"rect\",\n" +
				"    \"interlace\": false,\n" +
				"    \"height\": 1080.0\n" +
				"  }\n" +
				"}";


		JSONObject parse = (JSONObject) JSON.parse(s);
		JSONFactory factory = LinkedJSONFactory.getInstance();


		com.jtchen.json.JSON parse1 = factory.parse(s);

		System.out.println(parse);
		System.out.println(parse1);


		System.out.println(parse.get("name"));
		System.out.println(parse1.get("name"));



	}

	@Test
	public void test2() {
		String s = "{\"arr\": \"\\uD834\\uDD1E\"}";
		JSONObject parse = (JSONObject) JSON.parse(s);
		System.out.println(parse);

		System.out.println(parse.get("arr"));
	}
}
