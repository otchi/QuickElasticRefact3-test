package com.edifixio.amine.application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.application.elasticResults.SetSources;
import com.edifixio.amine.application.elasticResults.Source;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonObjectConfig;
import com.edifixio.amine.config.JsonPrimitiveConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.EntryImp;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class SimpleResponseMappingConfig extends JsonObjectConfig{
	

	

	public SimpleResponseMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
		// TODO Auto-generated constructor stub
	}
	
	public List<Object> getSourceObject(Class<?> responseBean,SetSources setSources) 
			throws NoSuchMethodException, SecurityException, NoSuchFieldException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException{
		
		List<Object> responseList=new LinkedList<Object>();
		Map<String,Entry<String,Method>> mapMethod= getSetters(responseBean);
		Iterator<Source> sourceIter=setSources.getSources().iterator();
		Iterator<Entry<String, JsonElement>> jsonSourceIter;
		Entry<String, JsonElement> entry;
		Entry<String,Method> entryMethod;
		Source  source;
		
		
		while(sourceIter.hasNext()){
			source=sourceIter.next();
			jsonSourceIter=source.getSources().entrySet().iterator();
			Object obj=responseBean.newInstance();
			
			while(jsonSourceIter.hasNext()){
				
				entry=jsonSourceIter.next();
				entryMethod=mapMethod.get(entry.getKey());
				
				if(entryMethod==null){
					System.out.println("exception 57 ~field "+entry.getKey()+" not maped");
					continue;
				}
				putField(entryMethod.getValue(), entryMethod.getKey(),entry.getValue() , obj);
			}
			responseList.add(obj);
			
		}

		return responseList;
	
	}
	
	public Map<String,Entry<String,Method>> getSetters(Class<?> responseBean)
			throws NoSuchMethodException, SecurityException, NoSuchFieldException{
		
		Map<String,Entry<String,Method>> map=new HashMap<String, Map.Entry<String,Method>>();
		Iterator<Entry<String, JsonElementConfig>> confIter=mapConfig.entrySet().iterator();
		Entry<String, JsonElementConfig> entry;
		String key, fieldname;
		JsonElementConfig value;
		Method method;
		
		
		while(confIter.hasNext()){
			entry=confIter.next();
			key=entry.getKey();
			value=entry.getValue();
			if(!((value.isPremitiveConfig()&&((JsonPrimitiveConfig)value).isStringConfig()))){
				System.out.println("not supported");
				return null;
			}
			fieldname=((JsonStringConfig)value).getValue();
			method=responseBean.getMethod("set"+fieldname.substring(0, 1).toUpperCase()+fieldname.substring(1), 
														responseBean.getDeclaredField(fieldname).getType());
			map.put(key, new EntryImp<String, Method>(fieldname, method));
		}
		return map;
	}
	
	
	public void putField(Method method,String fieldName,JsonElement jsonElement,Object obj)
					throws NoSuchFieldException, SecurityException,
							IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		
		Class<?> objClass=obj.getClass();
		Class<?> fieldClass=objClass.getDeclaredField(fieldName).getType();
		
		if(!jsonElement.isJsonPrimitive()){
			System.out.println("not supported");
			return;}
		JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
		
		if(fieldClass==int.class||fieldClass==Integer.class){
			if(!jp.isNumber()){ 
				System.out.println("exception");
				return;}
			method.invoke(obj, jp.getAsInt());

		}else
			if(fieldClass==double.class||fieldClass==Double.class){
				if(!jp.isNumber()){ 
					System.out.println("exception");
					return;}
				method.invoke(obj, jp.getAsDouble());
				
			}else 
				if(fieldClass==String.class){
					if(!jp.isString()){ 
						System.out.println("exception");
						return;}
					method.invoke(obj, jp.getAsString());
				}else if(fieldClass==boolean.class||fieldClass==Boolean.class){
					if(!jp.isBoolean()){ 
						System.out.println("exception");
						return;}
					method.invoke(obj, jp.getAsBoolean());
				}
		
	}

}
