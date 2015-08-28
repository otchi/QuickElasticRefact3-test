package test.com.edifixio.amine.configFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.google.gson.JsonParser;

import test.com.edifixio.amine.applicatif.TestRessources;

public class SpingInjectionTest {

	private ApplicationContext context;
	private static JsonParser jp=new JsonParser();
	

	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext(TestRessources.SPRING_CONFIGS
									+"BeansQuickElastic.xml");
	}
	
/**********************************************************************************************/
	
	@Test
	public void Test(){
		//JsonObjectConfigFactory jocf=
			//	((JsonObjectConfigFactory)
		DeclaredJsonObjectConfigFactory jocf=
				(DeclaredJsonObjectConfigFactory) context.getBean("main_config");
		Assert.assertTrue(true);

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
