package com.edifixio.amine.applicatif;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleRequestConfig extends JsonObjectConfig{
	private static final String CLASS="class";
	private static final String MAPPING="mapping";

	public SimpleRequestConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	public void process(Object object,JsonObject query){
		
		
			
		try {
			if (object.getClass()
					.equals(Class.forName(((JsonStringConfig) 
							mapConfig.get(CLASS)).getValue()))) {
				
				JsonElementConfig jec = mapConfig.get(MAPPING);
			
				if (!jec.isObjectConfig())
					System.out.println("execption");
				else
					((SimpleRequestMappingConfig) jec).process(query, object);
				
			} else {
				System.out.println("exception SimpleRequestConfig ~ 25");
				return;
			}
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
		}
			
	
		
	}

}
