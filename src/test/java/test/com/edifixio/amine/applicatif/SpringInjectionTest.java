package test.com.edifixio.amine.applicatif;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.object.TestObject;
import com.edifixio.amine.utils.JsonHandleUtil;
import com.google.gson.JsonObject;

public class SpringInjectionTest {
	private ApplicationContext context;
	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext("src/resource/BeansApplication.xml");
	
	}
	
/******************************************************************************************/
	@Test
	public void Test() {
		try {
			
			DeclaredJsonObjectConfigFactory djocf = (DeclaredJsonObjectConfigFactory) context.getBean("root_config");
			JsonObject jo = JsonHandleUtil.jsonFile("src/resource/query_voiture.json").getAsJsonObject();
			SimpleRootConfig src = (SimpleRootConfig) djocf.getJsonElementConfig(jo.get("_config"));
			JsonObject joq = jo.get("_init_query").getAsJsonObject();
			TestObject to = new TestObject();
			
			
			System.out.println(joq);
			src.process(joq);
			joq = jo.get("_query").getAsJsonObject();
			System.out.println(joq);
			src.process(joq);
			to.setField1("audi");
			src.process(joq, to, null);
			
			Assert.assertTrue(true);
			
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), false);
		}
	}

}
