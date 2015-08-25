package test.com.edifixio.amine.applicatif;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleResponseMappingConfig;
import com.edifixio.amine.application.elasticResults.SetSources;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.TestObject;
import com.google.gson.JsonParser;



public class SimpleResponseMappingConfigTest {
	private static final JsonParser JP=new JsonParser();
	private Map<String, JsonElementConfig> mapConfig;
	private Class<?> responseBean;
	private SetSources setSources;
	
	@Before
	public void init(){
		
		mapConfig=new HashMap<String, JsonElementConfig>();
		mapConfig.put("f1",new SimpleJsonStringConfig("field1"));
		mapConfig.put("f2",new SimpleJsonStringConfig("field2"));
		mapConfig.put("f3",new SimpleJsonStringConfig("field3"));
		
		responseBean=TestObject.class;
		
		setSources=SetSources.getSetSources(JP.parse(
				 "{"
				+ "hits:["
				+ "{_source:"
				+ "{"
				+ "f1:\"katia\","
				+ "f2:true,"
				+ "f3:55"
				+ "}"
				+ "}"
				+ "]"
				+ "}")
				.getAsJsonObject());
		
	}
	
	
	@Test
	public void test() throws NoSuchMethodException, SecurityException, 
		NoSuchFieldException, InstantiationException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException{
		
		SimpleResponseMappingConfig srmc=new SimpleResponseMappingConfig(mapConfig);
		List<Object> obj=srmc.getSourceObject(responseBean, setSources);
		System.out.println(obj);
		
	}

}
