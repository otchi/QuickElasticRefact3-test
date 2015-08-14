package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.config.SimpleRootConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonElementConfigFactoryState;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class DeclaredJsonObjectConfigFactoryTest {
	private Class<? extends JsonObjectConfig> classToFactory;
	private JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	private	Map<String, JsonElementConfigFactoryState> childFactories;

/**********************************************************************************************************************/	
	public DeclaredJsonObjectConfigFactoryTest(Class<? extends JsonObjectConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, Map<String, JsonElementConfigFactoryState> childFactories) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.childFactories = childFactories;
	}

/**********************************************************************************************************************/
	
	@Parameterized.Parameters
	public static Collection<?> testValues(){
		JsonPrimitiveTypeConfig jsPrimitiveTypeConfig=new JsonPrimitiveTypeConfig();
		jsPrimitiveTypeConfig.setStringConfig(JsonStringConfig.class);
		
		Map<String, JsonElementConfigFactoryState> childFactories =
				new HashMap<String, JsonElementConfigFactoryState>();
		
		childFactories.put("kaka",
				new JsonElementConfigFactoryState(
						new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig),
							false, false) 
				);
		
		childFactories.put("dd",new JsonElementConfigFactoryState(
				new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig),
				false, false));
		
		Map<String, JsonElementConfigFactoryState> childFactories1 =
				new HashMap<String, JsonElementConfigFactoryState>();
		
		childFactories1.put("kaka",
				new JsonElementConfigFactoryState(
						new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig),
							false, false) 
				);
		
		childFactories1.put("dd",new JsonElementConfigFactoryState(
				new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig),
				false, false));
		
		DeclaredJsonObjectConfigFactory subObject=
				new DeclaredJsonObjectConfigFactory(SimpleRootConfig.class, 
										jsPrimitiveTypeConfig,
										childFactories1);
		
		childFactories.put("tab", new JsonElementConfigFactoryState(
				subObject,false,false));
		
		
		return Arrays.asList(new Object[][]{
			{SimpleRootConfig.class,jsPrimitiveTypeConfig,childFactories}
		});
	}

/******************************************************************************************************************/
	
	@Test
	public void Test(){
		try {
		System.out.println(new DeclaredJsonObjectConfigFactory(classToFactory, jsPrimitiveTypeConfig, childFactories)
																.getJsonElementConfig(
																		new JsonParser()
																		.parse("{kaka:\"tt\",dd:\"popo\",tab:{kaka:\"tt\",dd:\"popo\"}}"
																				)).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
