package test.com.edifixio.amine.applicatif;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.application.ResultObject;
import com.edifixio.amine.application.SearchInElasctic;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.exception.QuickElasticException;
import com.edifixio.amine.object.TestObject;
import com.edifixio.amine.utils.Ressources;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.google.gson.JsonObject;

public class ElasticSearchTest {
	private static final String SPRING_CONFIG="BeansApplication.xml";
	private static final String MAIN_CONFIG_FACTORY="main_config";
	private final static   DeclaredJsonObjectConfigFactory  META_APPLI_CONFIG=
			( DeclaredJsonObjectConfigFactory )new FileSystemXmlApplicationContext(Ressources.RESOURCE_FLODER+SPRING_CONFIG)
																	.getBean(MAIN_CONFIG_FACTORY);
	//@Test
	public void matchAllTestRoot() throws ReflectiveOperationException, QuickElasticException, IOException{
		SimpleRootConfig application = 
				(SimpleRootConfig) META_APPLI_CONFIG.getJsonElementConfig(
						JsonHandleUtil.jsonFile(Ressources.JSON_QUERIES
								+"application_match_all_config.json").getAsJsonObject());
		

		JsonObject query=JsonHandleUtil.jsonFile(Ressources.JSON_QUERIES+"application_match_all.json")
				.getAsJsonObject();
		TestObject to=new TestObject();
		to.setField1("1");
		application.process(query,to);
		System.out.println(query);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getResultObject());
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getFacets());
	
		
		application.getBasedFacets().get("test").getBuckets().get("europe").setIsCheked(false);
		
		System.out.println(application.getResultObject());
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getFacets());
		
		application.process(query,null);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(query);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println(application.getBasedFacets());
		
	}
	
	//@Test
	public void test() throws ReflectiveOperationException, QuickElasticException, IOException{
		
		SearchInElasctic es=new SearchInElasctic(JsonHandleUtil.jsonFile(JsonObject.class,Ressources.JSON_QUERIES+"application_match_all_config.json"));
		TestObject to=new TestObject();
		to.setField1("2");
		JsonObject jsonObject=JsonHandleUtil.jsonFile(JsonObject.class,Ressources.JSON_QUERIES+"application_match_all.json");
		ResultObject ro=es.search(jsonObject, to);
		System.out.println(ro);
		ro.getFacets().get("test").getBuckets().get("us").setIsCheked(false);
		ro=es.search(jsonObject);
		System.out.println(jsonObject);
		System.out.println(ro);
		
		
	}
	
	@Test
	public void testWithBean() throws IOException{
		@SuppressWarnings("resource")
		ApplicationContext context=new FileSystemXmlApplicationContext(Ressources.RESOURCE_FLODER+
				"BeansPing.xml");
		SearchInElasctic es= (SearchInElasctic) context.getBean("search_in");
		TestObject to=new TestObject();
		to.setField1("2");
		JsonObject jsonObject=JsonHandleUtil.jsonFile(JsonObject.class,Ressources.JSON_QUERIES+"application_match_all.json");
		ResultObject ro=es.search(jsonObject, to);
		System.out.println(ro);
//		System.out.println(Ressources.JSON_QUERIES);
//		System.out.println(new File("src/resource/com/edifixio/amine/resource/json/query/application_match_all_config.json").exists());
	}

}

