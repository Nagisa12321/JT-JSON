import com.alibaba.fastjson.JSONObject;
import com.jtchen.factory.JSONFactory;
import com.jtchen.factory.impl.LinkedJSONFactory;
import com.jtchen.json.JSON;
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
		FileInputStream fis = new FileInputStream("src/main/resources/scaryJSON.json");
		StringBuilder builder = new StringBuilder();

		int data;
		while ((data = fis.read()) != -1) {
			builder.append((char) (data));
		}

		jsonStr = builder.toString();
		// logger.info("����ַ���: \n" + jsonStr);
	}


	// ���Թ���������
	@Test
	public void testInitialization() {

		JSONFactory factory = LinkedJSONFactory.getInstance();

		// ͨ����������һ��json����
		JSON json = factory.parse(jsonStr);

		System.out.println(json);


		// ���ҽ���
		JSON parse = factory.parse(json);

		System.out.println(parse);
	}
}
