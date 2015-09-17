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
 		mapConfigs.get(mapUnit1).put("f1", new SimpleJsonStringConfig("field1"));
 		
		
 		
 		
 		srmc=new SimpleResponseMappingConfig(mapConfigs.get(mapUnit1));
 		
	}
	
	@Test
	public void test(){
		init1();
		System.out.println(srmc);
		System.out.println(srmc.getLazyTree());
		
	}

}
