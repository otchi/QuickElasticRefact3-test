package com.edifixio.amine.applicatif;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.utils.ElasticClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

public class SimpleRootConfig  extends JsonObjectConfig{
	public static final String HOST="_host";
	public static final String INDEX="_index";

	public SimpleRootConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.mapConfig.toString();
	}
	
	
	public void process(JsonObjectConfig query,
						Object request,
						List<String> facets) throws IOException{
		
		
		
	
		JestClient jestClient=
			ElasticClient.getElasticClient(
				((SimpleJsonStringConfig)mapConfig
				.get(HOST))
				.getValue()).getClient();
	
		Builder builder=new Search.Builder(query.toString());
		((SimpleIndexConfig)mapConfig.get(INDEX)).process(builder);
		
		jestClient.execute(builder.build());
		
	}
	
	public void process(JsonObjectConfig initQuery){

}
	
	
	


}

