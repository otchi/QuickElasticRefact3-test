package com.edifixio.amine.application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleRequestMappingConfig extends JsonObjectConfig {

	public SimpleRequestMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}

	public void process(JsonObject query,Object request)
		throws NoSuchMethodException, SecurityException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException {
		
		MappingRequestResolver mrr=
				new MappingRequestResolver(query);
		mrr.parsing();
		
		Iterator<Entry<String, JsonElementConfig>> mapConfigIter=
										mapConfig.entrySet().iterator();
		
		Entry<String, JsonElementConfig> entry;
		String key;
		JsonElementConfig value;
	
		
		while(mapConfigIter.hasNext()){
			
			entry=mapConfigIter.next();
			key=entry.getKey();
			value=entry.getValue();
			//System.out.println("-->"+entry);
			Method method=
					request.getClass().getMethod("get"+
										key.substring(0, 1).toUpperCase()+
										key.substring(1));
			
			Object attr=method.invoke(request);
			//System.out.println("??"+attr.toString());
			//System.out.println("is premitive : "+entry+"--->"+value.isPremitiveConfig());
			//System.out.println("is array config : "+entry+"--->"+value.isArrayConfig());
			if(value.isPremitiveConfig()){
				primitiveElementProcess(mrr,(JsonPrimitiveConfig) value, attr.toString());
				//System.out.println("!!!"+value);
			}else{
				if(value.isArrayConfig()){
					JsonArrayConfig jac=(JsonArrayConfig) value;
					for(JsonElementConfig jec:jac.getJsonElementConfigs()){
						//System.out.println("###"+jec);
						if(jec.isPremitiveConfig()){
							primitiveElementProcess(mrr,(JsonPrimitiveConfig) jec, attr.toString());
						}else{ System.out.println("not supported ");}
					}	
				}else{ System.out.println("not supported ");}
			}
			//System.out.println(entry+"--->"+query);
		}

		
	}
	
	private void primitiveElementProcess(MappingRequestResolver mrr,JsonPrimitiveConfig jpc,String input){
		
		System.out.println(mrr.getCorresp());
		if(jpc.isStringConfig()){
			JsonStringConfig jsc=(JsonStringConfig)jpc;
			mrr.varResolver(jsc.getValue(), input);
			
		}else{
			System.out.println("exception - not supported");
		}
		
	}
	
}
