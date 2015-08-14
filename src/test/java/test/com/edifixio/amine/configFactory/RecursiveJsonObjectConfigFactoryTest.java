package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.config.SimpleRootConfig;
import com.edifixio.amine.configFactory.RecursiveJsonObjectConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class RecursiveJsonObjectConfigFactoryTest {
	private Class<? extends JsonObjectConfig> classToFactory;
	private JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	
	public RecursiveJsonObjectConfigFactoryTest(
			Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
	}
	
	@Parameterized.Parameters
	public static Collection<?> tetesValues(){
		JsonPrimitiveTypeConfig jsPrimitiveTypeConfig=new JsonPrimitiveTypeConfig();
		jsPrimitiveTypeConfig.setStringConfig(JsonStringConfig.class);
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPrimitiveTypeConfig}});
	}
/******************************************************************************************************************/
	
	@Test
	public void Test(){
		try {
		System.out.println(new RecursiveJsonObjectConfigFactory(classToFactory, jsPrimitiveTypeConfig)
																.getJsonElementConfig(
																		new JsonParser()
																		.parse("{kaka:\"tt\",dd:\"popo\",tab:{kaka:{bb:\"tt\"},dd:\"popo\"}}"
																				)).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	

}
