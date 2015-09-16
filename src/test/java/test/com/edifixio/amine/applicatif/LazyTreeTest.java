package test.com.edifixio.amine.applicatif;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.edifixio.amine.application.SimpleJsonBooleanConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleResponseConfig;
import com.edifixio.amine.application.SimpleResponseConfigUnit;
import com.edifixio.amine.application.SimpleResponseMappingConfig;
import com.edifixio.amine.config.JsonElementConfig;

public class LazyTreeTest {
	private SimpleResponseMappingConfig srmc;

	/****** test a refaire */////////////////////////////
	public void init1(){
		Map<String,Map<String, JsonElementConfig>> mapConfigs=new HashMap<String, Map<String,JsonElementConfig>>();
		
		String mapUnit1="mapUnit1";
 		mapConfigs.put(mapUnit1, new HashMap<String, JsonElementConfig>());
 		//mapConfigs.get(mapUnit1).put("f1", new SimpleJsonStringConfig("field1"));
 		
		
		String mapUnit2="mapUnit2";
 		mapConfigs.put(mapUnit2, new HashMap<String, JsonElementConfig>());
 		mapConfigs.get(mapUnit2).put("name", new SimpleJsonStringConfig("field4"));
 		mapConfigs.get(mapUnit2).put("lazy", new SimpleJsonBooleanConfig(false));
 		mapConfigs.get(mapUnit1).put("f4", new SimpleResponseConfigUnit(mapConfigs.get(mapUnit2)));
 		
 		String mapUnit21="mapUnit21";
 		mapConfigs.put(mapUnit21, new HashMap<String, JsonElementConfig>());
 		mapConfigs.get(mapUnit2).put("mapping", new SimpleResponseConfigUnit(mapConfigs.get(mapUnit21)));
 		
 		
		String mapUnit3="mapUnit3";
 		mapConfigs.put(mapUnit3, new HashMap<String, JsonElementConfig>());
 		mapConfigs.get(mapUnit3).put("name", new SimpleJsonStringConfig("field5"));
 		mapConfigs.get(mapUnit3).put("lazy", new SimpleJsonBooleanConfig(true));
 		mapConfigs.get(mapUnit21).put("f5", new  SimpleResponseConfigUnit(mapConfigs.get(mapUnit3)));
 		
 		srmc=new SimpleResponseMappingConfig(mapConfigs.get(mapUnit1));
 		
	}
	
	@Test
	public void test(){
		init1();
		System.out.println(srmc);
		System.out.println(srmc.getLazyTree());
		
	}

}
