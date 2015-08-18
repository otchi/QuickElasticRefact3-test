package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.applicatif.SimpleRootConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.configFactory.RecursiveJsonObjectConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class RecursiveJsonObjectConfigFactoryTest {
	private Class<? extends JsonObjectConfig> classToFactory;
	private JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	private String jsonString;
	
	public RecursiveJsonObjectConfigFactoryTest(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			String jsonString) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.jsonString=jsonString;
	}
	
	@Parameterized.Parameters
	public static Collection<?> tetesValues(){
		JsonPrimitiveTypeConfig jsPrimitiveTypeConfig=new JsonPrimitiveTypeConfig();
		jsPrimitiveTypeConfig.setStringConfig(JsonStringConfig.class);
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPrimitiveTypeConfig,
				"{kaka:\"tt\",dd:\"popo\",tab:{kaka:{bb:\"tt\"},dd:\"popo\"}}"}});
	}
/******************************************************************************************************************/
	
	@Test
	public void Test(){
		try {
		
			int objectLenght=
					TestUtils.RemoveWhiteChar(
					new RecursiveJsonObjectConfigFactory(
						classToFactory, jsPrimitiveTypeConfig)
											.getJsonElementConfig(
														new JsonParser()
																.parse(jsonString)).toString()).length();
			int jsonlenght =TestUtils.RemoveWhiteChar(jsonString).length();
			
			
			Assert.assertEquals(objectLenght, jsonlenght);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	

}
