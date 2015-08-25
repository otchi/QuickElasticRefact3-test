package test.com.edifixio.amine.applicatif;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.application.SimpleIndexConfig;
import com.edifixio.amine.application.SimpleJsonStringConfig;
import com.edifixio.amine.application.SimpleTypeIndexConfig;
import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.utils.ElasticClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;
import io.searchbox.core.SearchResult;

public class SimpleIndexConfigTest {
	private static final String HOST="http://localhost:9200";
	private SimpleIndexConfig sic;
	private JestClient jestClient;
	private String query="";
	@Before
	public void init() throws IOException{
		jestClient=ElasticClient.getElasticClient(HOST).getClient();
		
		JsonArrayConfig jac1=new SimpleTypeIndexConfig();
		jac1.addJsonElementConfig(new SimpleJsonStringConfig("vehicule"));
		//jac1.addJsonElementConfig(new SimpleJsonStringConfig("jeux"));
		
		//JsonArrayConfig jac2=new SimpleTypeIndexConfig();
		//jac2.addJsonElementConfig(new SimpleJsonStringConfig("strategy"));
		//jac2.addJsonElementConfig(new SimpleJsonStringConfig("carte"));
		
		
		Map<String, JsonElementConfig> mapConf=new HashMap<String, JsonElementConfig>();
		mapConf.put("names", jac1);
		//mapConf.put("types", jac2);
		
		
		sic=new SimpleIndexConfig(mapConf);
		
		
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(
				new FileReader(
						new File("src/resource/query_voiture.json")));
		String buff;
		while((buff=br.readLine())!=null){
			
			query+=buff+"\n";
		}
	
	}
	
	@Test
	public void test() throws IOException{
		System.out.println(query);
		Builder builder=new Search.Builder(query);
		sic.process(builder);
		SearchResult result=jestClient.execute(builder.build());
		//System.out.println(result.getAggregations().getTermsAggregation("ff"));
		//System.out.println(result.getAggregations().getRangeAggregation("22"));
		System.out.println(result.getJsonString());
		
	}

}