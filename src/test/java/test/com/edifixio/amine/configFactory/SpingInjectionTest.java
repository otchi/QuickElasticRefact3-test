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
	public static final String CONFIG1="{_host:\"katia\","
			+ " _request:{class:\"cc\",mapping:{tt:[\"dd\"]}},"
			+ "_response:{class:\"cc\"},_index:{cc:[]}}"; 
	
	public static final String CONFIG2="{\"_facets\":[\"cdf\",{facet_name:\"cvfdr\",sub_facets:[\"bb\"]}],"
			+ "\"_host\":\"ccc\""
			+ ",\"_request\":{\"class\":\"cdcdcdcfdcd\""
			+ ",\"mapping\":{"
			+ "\"derf\":[\"sdfggf\"]}"
			+ "}"
			+ ",_response:{\"class\":\"cddcdcdfss\""
			+ ",\"mapping\":{\"katia\":\"cccc\""
			+ ",\"amine\":\"cc\""
			+ ",\"man\":{"
			+ "\"name\":\"ccc\""
			+ ",\"mapping\":{"
			+ "\"cc\":{"
			+ "\"name\":\"bffd\""
			+ ",\"mapping\":{\"dst\":\"brm\"}}}}"
			+ "}}}";
	private ApplicationContext context;
	private static JsonParser jp=new JsonParser();
	

	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext(TestRessources.RESOURCE_FLODER +"BeansQuickElastic.xml");
	}
	
/**********************************************************************************************/
	
	@Test
	public void Test(){
		/*UnlimitedJsonObjectConfigFactory jocf=
				(UnlimitedJsonObjectConfigFactory) context.getBean("mapping_response");*/
	
		DeclaredJsonObjectConfigFactory jocf=
				(DeclaredJsonObjectConfigFactory) context.getBean("main_config");
		Assert.assertTrue(true);

		try {
			System.out.println(jocf.getJsonElementConfig(jp.parse(CONFIG2)));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
