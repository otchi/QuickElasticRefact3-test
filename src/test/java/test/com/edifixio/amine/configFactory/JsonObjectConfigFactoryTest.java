package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.conficFactory.JsonElementConfigFactory;
import com.edifixio.amine.conficFactory.JsonObjectConfigFactory;
import com.edifixio.amine.conficFactory.JsonPrimitiveConfigFactory;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.config.SimpleRootConfig;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class JsonObjectConfigFactoryTest {
	private Class<? extends JsonObjectConfig> classToFactory;
	private JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	private	Map<String, JsonElementConfigFactory> childFactories;
	
	public JsonObjectConfigFactoryTest(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, Map<String, JsonElementConfigFactory> childFactories) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.childFactories = childFactories;
	}
	
	@Parameterized.Parameters
	public static Collection<?> testValues(){
		JsonPrimitiveTypeConfig jsPrimitiveTypeConfig=new JsonPrimitiveTypeConfig();
		jsPrimitiveTypeConfig.setStringConfig(JsonStringConfig.class);
		Map<String, JsonElementConfigFactory> childFactories =new HashMap<String, JsonElementConfigFactory>();
		childFactories.put("kaka", new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig));
		childFactories.put("dd", new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig));
		
		JsonObjectConfigFactory subObject=new JsonObjectConfigFactory(SimpleRootConfig.class, jsPrimitiveTypeConfig, false, false, childFactories);
		childFactories.put("tab", subObject);
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPrimitiveTypeConfig,childFactories}
		});
	}


	@Test
	public void Test(){
		try {
		System.out.println(new JsonObjectConfigFactory(classToFactory, jsPrimitiveTypeConfig, false, false, childFactories)
																.getJsonElementConfig(new JsonParser().parse("{kaka:\"tt\",dd:\"popo\",tab:{kaka:\"tt\",dd:\"popo\"}}")).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
