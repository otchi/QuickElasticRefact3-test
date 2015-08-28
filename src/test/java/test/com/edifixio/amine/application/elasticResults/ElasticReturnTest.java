package test.com.edifixio.amine.application.elasticResults;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.edifixio.amine.application.elasticResults.ElasticReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import test.com.edifixio.amine.applicatif.TestRessources;

public class ElasticReturnTest {
	private JsonObject jsonObject;
	
	
	@Before
	public void init() throws IOException{
		String query="";
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(
				new FileReader(
						new File(TestRessources.JSON_RESPONSES+"my_response1.json")));
		String buff;
		while((buff=br.readLine())!=null){
			
			query+=buff+"\n";
		}
		jsonObject=new JsonParser().parse(query).getAsJsonObject();
		
	}
	
	@Test
	public void test(){
		ElasticReturn elasticReturn=ElasticReturn.getElasticReturn(jsonObject);
		System.out.println(elasticReturn.getSetSources().getSources().get(0).getSources());
		Assert.assertTrue(true);
		
	}

}
