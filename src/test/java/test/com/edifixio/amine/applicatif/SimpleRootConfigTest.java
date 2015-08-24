package test.com.edifixio.amine.applicatif;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.edifixio.amine.application.SimpleIndexConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleRequestConfig;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.application.SimpleTypeIndexConfig;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.object.TestObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(Parameterized.class)
public class SimpleRootConfigTest {
	private Map<String, JsonElementConfig> mapConfig;
	private JsonObject query;
	private Object request;
	@SuppressWarnings("unused")
	private List<String> facets;
	
	public SimpleRootConfigTest(
			Map<String, JsonElementConfig> mapConfig,
			JsonObject query, Object request,
			List<String> facets) {
		
		super();
		this.mapConfig = mapConfig;
		this.query = query;
		this.request = request;
		this.facets = facets;
	}
	
	@Parameterized.Parameters
	public static Collection<?> dataSet() throws IOException{

		TestObject obj=new TestObject();
		obj.setField1("audi");

		String query="";
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(
				new FileReader(
						new File("src/resource/my_request1.json")));
		String buff;
		while((buff=br.readLine())!=null){
			
			query+=buff+"\n";
		}

		JsonObject jo=new JsonParser().parse(query)
										.getAsJsonObject();
		SimpleRequestConfig src=SimpleRequestConfigTest.daraSet();
		
		JsonArrayConfig jac1=new SimpleTypeIndexConfig();
		jac1.addJsonElementConfig(new SimpleJsonStringConfig("vehicule"));
		Map<String, JsonElementConfig> m=new HashMap<String, JsonElementConfig>();
		m.put("names", jac1);
		SimpleIndexConfig sic=new SimpleIndexConfig(m);
		
		Map<String, JsonElementConfig> mapConfig=new HashMap<String, JsonElementConfig>();
		mapConfig.put("_host", new SimpleJsonStringConfig("http://localhost:9200"));
		mapConfig.put("_index", sic);
		mapConfig.put("_request", src);
		
		return Arrays.asList(new Object[][]{
			{mapConfig,jo,obj,null}
		});
	}
	
	@Test
	public void test(){
		
		SimpleRootConfig sRootc=new SimpleRootConfig(mapConfig);
		try {
			sRootc.process(query,request,null);
			Assert.assertTrue(true);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}


}
