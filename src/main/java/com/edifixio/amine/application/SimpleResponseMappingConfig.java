package com.edifixio.amine.application;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SimpleResponseMappingConfig extends JsonObjectConfig{
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public SimpleResponseMappingConfig(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public List<Object> getSourceObject(Class<?> responseBean,SetSources setSources) throws ReflectiveOperationException{
		
		List<Object> responseList=new LinkedList<Object>();
		Map<String,Entry<String,Method>> mapMethod= getSetters(responseBean);
		Iterator<Source> sourceIter=setSources.getSources().iterator();
	
		while(sourceIter.hasNext()){
		
			responseList.add(putJsonInObject(sourceIter.next().getSources(), responseBean, mapMethod));
		}

		return responseList;
	
	}
	
	public Object putJsonInObject(JsonObject jsonObject, Class<?> responseBean,
		Map<String, Entry<String, Method>> mapMethod) throws ReflectiveOperationException {
		
		Iterator<Entry<String, JsonElement>> jsonSourceIter;
		Entry<String, JsonElement> entry;
		Entry<String, Method> entryMethod;
		Object obj;

		jsonSourceIter = jsonObject.entrySet().iterator();
		obj = responseBean.newInstance();

		while (jsonSourceIter.hasNext()) {

			entry = jsonSourceIter.next();

			if (!mapMethod.containsKey(entry.getKey())) {
				System.out.println("exception 57 ~field " + entry.getKey() + " not maped");
				continue;
			}
			entryMethod = mapMethod.get(entry.getKey());
			putField(entryMethod.getValue(), entryMethod.getKey(), entry.getValue(), obj);
		}

		return obj;
	}
	
	/****************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public Map<String,Entry<String,Method>> getSetters(Class<?> responseBean)throws ReflectiveOperationException{
		
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
	
	/********************************************************************************************************************/
	/*******************************************************************************************************************/
	/********************************************************************************************************************/
	public void putField(Method method,String fieldName,JsonElement jsonElement,Object obj)throws ReflectiveOperationException{
		
		Class<?> objClass=obj.getClass();
		Class<?> fieldClass=objClass.getDeclaredField(fieldName).getType();
		
		if(!jsonElement.isJsonPrimitive()){
			System.out.println("not supported");// change here code to process complex object 
			return;
		}
		JsonPrimitive jp=jsonElement.getAsJsonPrimitive();
		
		if(fieldClass==int.class||fieldClass==Integer.class){
			if(!jp.isNumber()){ 
				System.out.println("exception SimpleResponseMappingConfig ~ can't put"+jsonElement+" in integer filed "+fieldName);
				return;
			}
			method.invoke(obj, jp.getAsInt());

		}else
			if(fieldClass==double.class||fieldClass==Double.class){
				if(!jp.isNumber()){ 
					System.out.println("exception SimpleResponseMappingConfig ~ can't put"+jsonElement+" in double filed "+fieldName);
					return;}
				method.invoke(obj, jp.getAsDouble());
				
			}else 
				if(fieldClass==String.class){
					if(!jp.isString()){ 
						System.out.println("exception SimpleResponseMappingConfig ~ can't put"+jsonElement+" in String filed "+fieldName);
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
