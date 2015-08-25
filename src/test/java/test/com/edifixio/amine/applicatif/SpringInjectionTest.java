package test.com.edifixio.amine.applicatif;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;

public class SpringInjectionTest {
	
	private ApplicationContext context;
	//private static JsonParser jp=new JsonParser();
	

	
/*********************************************************************************************/

	@Before
	public void loadConfig(){
		context=new FileSystemXmlApplicationContext("src/resource/BeansApplication.xml");
		DeclaredJsonObjectConfigFactory djocf=
				(DeclaredJsonObjectConfigFactory) context.getBean("root_config");
	}
	
/**********************************************************************************************/
	
	@Test
	public void Test(){
		
		Assert.assertTrue(true);
		
	}

}
