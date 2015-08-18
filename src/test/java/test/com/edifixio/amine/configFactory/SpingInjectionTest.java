package test.com.edifixio.amine.configFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.JsonObjectConfigFactory;
import com.google.gson.JsonParser;

public class SpingInjectionTest {

	private ApplicationContext context;
	private static JsonParser jp=new JsonParser();
	

	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext("src/resource/BeansQuickElastic.xml");
	}
	
/**********************************************************************************************/
	
	@Test
	public void Test(){
		JsonObjectConfigFactory jocf=
				((JsonObjectConfigFactory)
						context.getBean("main_config"));

		try {
			System.out.println(jocf.getJsonElementConfig(jp.parse("{_host:\"katia\","
					+ " _request:{class:\"cc\",mapping:{tt:[\"dd\"]}},"
					+ "_response:{class:\"cc\",mapping:{}},_index:{cc:[]}}")));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
