package test.com.edifixio.amine.applicatif;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.applicatif.SimpleJsonArrayConfig;
import com.edifixio.amine.applicatif.SimpleJsonStringConfig;
import com.edifixio.amine.applicatif.SimpleRequestMappingConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.MyTestObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class SimpleRequestMappingConfigTest {
	private Map<String, JsonElementConfig> mapConf;
	private Object request;
	private JsonObject jsonObject;
	
	public SimpleRequestMappingConfigTest(
			Map<String, JsonElementConfig> mapConf,
			Object request, 
			JsonObject jsonObject) {
		
		super();
		this.mapConf = mapConf;
		this.request = request;
		this.jsonObject = jsonObject;
	}
	@Parameterized.Parameters
	public static Collection<?> testValue(){
		
		Map<String, JsonElementConfig> mapConf=
				new HashMap<String, JsonElementConfig>();
		SimpleJsonArrayConfig sac=new SimpleJsonArrayConfig();
		sac.addJsonElementConfig(new SimpleJsonStringConfig("amine"));
		sac.addJsonElementConfig(new SimpleJsonStringConfig("katia"));
		mapConf.put("field1", sac);
		mapConf.put("field2", new SimpleJsonStringConfig("ouardia"));
		
		MyTestObject myTestObject=new MyTestObject();
		myTestObject.setField1("frére");
		myTestObject.setField2(true);
		
		
		
		JsonObject jo=new JsonParser().parse(
				"{s:\"test1 ${ouardia}\"}")
				.getAsJsonObject();
		JsonObject jo1=new JsonParser().parse(
				"{s:\"test1 ${ouardia}\","
				+ " object1:{"
				+ "array1:[\"test2 : ${amine}\",{katiaField:\"${katia} test3\"}]}}")
				.getAsJsonObject();
		return Arrays.asList(new Object[][]{
			{mapConf,myTestObject,jo}//,{mapConf,myTestObject,jo1}
		});
	}
	
	@Test
	public void test(){
		System.out.println("_______________________________________________________________");
		try {
			new SimpleRequestMappingConfig(mapConf).process( jsonObject,request);
			System.out.println("json substitution"+jsonObject);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Assert.assertTrue(true);
		System.out.println("_______________________________________________________________");
	}
	


	
}
