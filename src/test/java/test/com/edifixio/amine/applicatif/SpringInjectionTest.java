package test.com.edifixio.amine.applicatif;

import org.junit.Test;

public class SpringInjectionTest {
	
	/*private ApplicationContext context;
	
	/*********************************************************************************************/

	/*@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext(Ressources.RESOURCE_FLODER+
													"BeansApplication.xml");
	
	}
	
	/******************************************************************************************/
	@Test
	public void Test() {
		//try {
			
			/**DeclaredJsonObjectConfigFactory djocf =
					(DeclaredJsonObjectConfigFactory)
							context.getBean("main_config");
			JsonObject jo = JsonHandleUtil.jsonFile(
					Ressources.JSON_QUERIES+"query_voiture.json").getAsJsonObject();
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
		}*/
	}

}
