package test.com.edifixio.amine.configFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.JsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
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
						context.getBean("MainConfig"));
		
			JsonPrimitiveConfigFactory jpcf=((JsonPrimitiveConfigFactory)
				context.getBean("simple_Str_PCF"));
		try {
			System.out.println(jpcf.getJsonElementConfig(jp.parse("a")));
			System.out.println(jocf.getJsonElementConfig(jp.parse("{_host:\"katia\","
					+ " _facets:[\"cc\"]"
					+ ", _request:{ class:\"String\",mapping:\"bouhou\"}"
					+ "}")));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
