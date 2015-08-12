package test.com.edifixio.amine.configFactory;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.conficFactory.JsonArrayConfigFactory;
import com.edifixio.amine.conficFactory.JsonObjectConfigFactory;
import com.edifixio.amine.conficFactory.JsonPrimitiveConfigFactory;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.config.SimpleJsonArrayConfig;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class JsonArrayConfigFactoryTest {
	private Class<? extends JsonArrayConfig> classToFactory;
	private JsonPrimitiveTypeConfig jsPrimitiveTypeConfig;
	private JsonArrayConfigFactory jArrayConfigFactory;
	private JsonObjectConfigFactory jObjectConfigFactory;
	private JsonPrimitiveConfigFactory jPremitiveConfigFactory;
	
	public JsonArrayConfigFactoryTest(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig, JsonArrayConfigFactory jArrayConfigFactory,
			JsonObjectConfigFactory jObjectConfigFactory, JsonPrimitiveConfigFactory jPremitiveConfigFactory) {
		super();
		this.classToFactory = classToFactory;
		this.jsPrimitiveTypeConfig = jsPrimitiveTypeConfig;
		this.jArrayConfigFactory = jArrayConfigFactory;
		this.jObjectConfigFactory = jObjectConfigFactory;
		this.jPremitiveConfigFactory = jPremitiveConfigFactory;
	}

	@Parameterized.Parameters
	public static Collection<?> inputParam(){
		JsonPrimitiveTypeConfig jPrimitiveTypeConfig=new JsonPrimitiveTypeConfig();
		jPrimitiveTypeConfig.setStringConfig(JsonStringConfig.class);
		JsonPrimitiveConfigFactory jConfigFactory=new JsonPrimitiveConfigFactory(jPrimitiveTypeConfig);		
		return Arrays.asList(new Object[][]{
			{SimpleJsonArrayConfig.class,jPrimitiveTypeConfig,null,null,jConfigFactory}
		});

	}




	@Test
	public void SimpleTest(){
		JsonArrayConfigFactory jacf=new JsonArrayConfigFactory(classToFactory, 
				jsPrimitiveTypeConfig, false, false, jArrayConfigFactory, jObjectConfigFactory, jPremitiveConfigFactory);
		try {
			Assert.assertEquals("[amine]", jacf.getJsonElementConfig(new JsonParser().parse("[\"amine\"]")).toString());
			Assert.assertEquals("amine", jacf.getJsonElementConfig(new JsonParser().parse("amine")).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
