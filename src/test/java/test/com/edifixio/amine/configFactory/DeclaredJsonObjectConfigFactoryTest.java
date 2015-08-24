package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.TypesJsonPrimitiveConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonElementConfigFactoryState;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;



@RunWith(Parameterized.class)
public class DeclaredJsonObjectConfigFactoryTest {
	private static final JsonParser JP=new JsonParser();
	private Class<? extends JsonObjectConfig> classToFactory;
	private TypesJsonPrimitiveConfig jsPrimitiveTypeConfig;
	private	Map<String, JsonElementConfigFactoryState> childFactories;
	private String jsonString;

/**********************************************************************************************************************/	
	public DeclaredJsonObjectConfigFactoryTest(
			Class<? extends JsonObjectConfig> classToFactory,
			TypesJsonPrimitiveConfig jsPrimitiveTypeConfig, 
			Map<String, JsonElementConfigFactoryState> childFactories,
			String jsonString) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.childFactories = childFactories;
		this.jsonString=jsonString;
	}

/**********************************************************************************************************************/
	
	@Parameterized.Parameters
	public static Collection<?> testValues(){
		TypesJsonPrimitiveConfig jsPrimitiveTypeConfig=new TypesJsonPrimitiveConfig();
		jsPrimitiveTypeConfig.setStringConfig(SimpleJsonStringConfig.class);
		
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
			{SimpleRootConfig.class,jsPrimitiveTypeConfig,childFactories,
				"{kaka:\"tt\",dd:\"popo\",tab:{kaka:\"tt\",dd:\"popo\"}}"}
		});
	}

/******************************************************************************************************************/
	
	@Test
	public void Test(){
		
		try {
			int objectLenght=TestUtils.RemoveWhiteChar(
	    		 			new DeclaredJsonObjectConfigFactory(
	    		 					classToFactory, jsPrimitiveTypeConfig, childFactories)
	    		 			.getJsonElementConfig(JP.parse(jsonString)).toString()).length();
			int jsonlenght=TestUtils.RemoveWhiteChar(jsonString).length();
			Assert.assertEquals(jsonlenght, objectLenght);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
