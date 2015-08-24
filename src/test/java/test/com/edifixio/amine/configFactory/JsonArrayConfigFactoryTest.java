package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleJsonArrayConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.TypesJsonPrimitiveConfig;
import com.edifixio.amine.configFactory.DeclaredJsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonArrayConfigFactory;
import com.edifixio.amine.configFactory.JsonElementConfigFactoryState;
import com.edifixio.amine.configFactory.JsonObjectConfigFactory;
import com.edifixio.amine.configFactory.JsonPrimitiveConfigFactory;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class JsonArrayConfigFactoryTest {
	private Class<? extends JsonArrayConfig> classToFactory;
	private TypesJsonPrimitiveConfig jsPrimitiveTypeConfig;
	private JsonArrayConfigFactory jArrayConfigFactory;
	private JsonObjectConfigFactory jObjectConfigFactory;
	private JsonPrimitiveConfigFactory jPremitiveConfigFactory;
/****************************************************************************************************************************/	
	public JsonArrayConfigFactoryTest(Class<? extends JsonArrayConfig> classToFactory,
			TypesJsonPrimitiveConfig jsPrimitiveTypeConfig, JsonArrayConfigFactory jArrayConfigFactory,
			JsonObjectConfigFactory jObjectConfigFactory, JsonPrimitiveConfigFactory jPremitiveConfigFactory) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.jArrayConfigFactory = jArrayConfigFactory;
		this.jObjectConfigFactory = jObjectConfigFactory;
		this.jPremitiveConfigFactory = jPremitiveConfigFactory;
	}
	
/*********************************************************************************************************************************/

	@Parameterized.Parameters
	public static Collection<?> inputParam(){
		TypesJsonPrimitiveConfig jPrimitiveTypeConfig=new TypesJsonPrimitiveConfig();
		
		jPrimitiveTypeConfig.setStringConfig(SimpleJsonStringConfig.class);
		
		JsonPrimitiveConfigFactory jConfigFactory=new JsonPrimitiveConfigFactory(jPrimitiveTypeConfig);	
		
		Map<String, JsonElementConfigFactoryState> childFactories=new HashMap<String, JsonElementConfigFactoryState>();
		
		childFactories.put("dd",
				new JsonElementConfigFactoryState(
						new JsonPrimitiveConfigFactory(jPrimitiveTypeConfig), false, false));
			
		
		JsonObjectConfigFactory jObjectConfigFactory=new DeclaredJsonObjectConfigFactory(SimpleRootConfig.class, 
																					jPrimitiveTypeConfig, 
																					childFactories);
		return Arrays.asList(new Object[][]{
			{SimpleJsonArrayConfig.class,jPrimitiveTypeConfig,null,jObjectConfigFactory,jConfigFactory}
		});
	}

/*****************************************************************************************************************************/

	@Test
	public void SimpleTest(){
		String ja="[\"amine\",{dd:\"kk\"}]";
		JsonArrayConfigFactory jacf=new JsonArrayConfigFactory(classToFactory, 
				jsPrimitiveTypeConfig, jArrayConfigFactory, jObjectConfigFactory, jPremitiveConfigFactory);
		try {
			Assert.assertEquals(TestUtils.RemoveWhiteChar(ja).length(),
					TestUtils.RemoveWhiteChar(
							jacf.getJsonElementConfig(
									new JsonParser().parse(ja)).toString()).length());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
