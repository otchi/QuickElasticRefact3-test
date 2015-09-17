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
import com.edifixio.amine.utils.ConfigFactoryUtiles;
import com.edifixio.amine.utils.EntryImp;
import com.edifixio.amine.utils.JsonPathTree;
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
	/******************************************************************************************************************/
	public JsonPathTree getLazyTree(){
		JsonPathTree jsonPathTree=new JsonPathTree("", "", false);
		this.lazyTreeProcess(jsonPathTree);
		return jsonPathTree;
	}
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	protected void lazyTreeProcess(JsonPathTree jsonPathTree){
		String prentPath=jsonPathTree.getElement()+jsonPathTree.getName();
		Iterator<Entry<String, JsonElementConfig>> mapConfigIter=mapConfig.entrySet().iterator();
		Entry<String, JsonElementConfig> entry;
		
		while(mapConfigIter.hasNext()){
			entry=mapConfigIter.next();
			JsonElementConfig jsc=entry.getValue();
			if(jsc.isObjectConfig()){
		
				jsonPathTree.addChild(
						(((SimpleResponseConfigUnit)jsc).getLazyTree(entry.getKey(),prentPath)));
			}
		}	
	}
	
	
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public List<Object> getSourceObject(Class<?> responseClass,SetSources setSources) throws ReflectiveOperationException{
		
		List<Object> responseList=new LinkedList<Object>();
		Map<String,Entry<String,Method>> mapMethod= getSetters(responseClass);
		Iterator<Source> sourceIter=setSources.getSources().iterator();
	
		while(sourceIter.hasNext()){
			responseList.add(putJsonInObject(sourceIter.next().getSources(),  responseClass, mapMethod));
		}

		return responseList;
	}
	
	public Object getSourceObject(Class<?> responseClass,JsonObject jsonObject) throws ReflectiveOperationException{
		
		
		Map<String,Entry<String,Method>> mapMethod= getSetters(responseClass);//this code is executed several times for same result
		//System.out.println("--->>"+mapMethod+"////"+responseClass);
		//System.out.println(jsonObject+"//"+responseClass+"//"+mapMethod);
		return putJsonInObject(jsonObject,  responseClass, mapMethod);
		
	}
	/******************************************************************************************************/
	/******************************************************************************************************/
	/******************************************************************************************************/
	public Object putJsonInObject(JsonObject jsonObject, Class<?>  responseClass,
		Map<String, Entry<String, Method>> mapMethod) throws ReflectiveOperationException {
		
		Object resultObj =  responseClass.newInstance();
		
		Iterator<Entry<String, JsonElement>> jsonSourceIter;
		Entry<String, JsonElement> entry;
		Entry<String, Method> entryMethod;
		
		jsonSourceIter = jsonObject.entrySet().iterator();
		/***********************************/
		while (jsonSourceIter.hasNext()) {

			entry = jsonSourceIter.next();

			if (!mapMethod.containsKey(entry.getKey())) {
				System.out.println("exception 57 ~field " + entry.getKey() + " not mapped");
				continue;
			}
			
			entryMethod = mapMethod.get(entry.getKey());
		
			if(entryMethod!=null){
				putField(entryMethod.getValue(), entryMethod.getKey(), entry.getValue(), resultObj,null);
			}else{
				putField( null, null, entry.getValue(),resultObj,entry.getKey());
			}
		}

		return resultObj;
	}
	
	/****************************************************************************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public Map<String,Entry<String,Method>> getSetters(Class<?>  responseClass)throws ReflectiveOperationException{
		//System.out.println("++"+mapConfig);
		Map<String,Entry<String,Method>> map=new HashMap<String, Map.Entry<String,Method>>();
		Iterator<Entry<String, JsonElementConfig>> confIter=mapConfig.entrySet().iterator();
		Entry<String, JsonElementConfig> entry;
		String  fieldname;
		JsonElementConfig value;
		
		
		while(confIter.hasNext()){
			entry=confIter.next();
			value=entry.getValue();
			
			
			if(!((value.isPremitiveConfig()&&((JsonPrimitiveConfig)value).isStringConfig()))){
				if(!value.isObjectConfig()){
					System.out.println("not supported");
					return null;
				}
				map.put(entry.getKey(), null);
				continue;
			}
			fieldname=((JsonStringConfig)value).getValue();
			map.put(entry.getKey(), new EntryImp<String, Method>(fieldname,getSetter(responseClass, fieldname)));
		}
		return map;
	}
	
	
	public static  Method getSetter(Class<?>  responseClass,String fieldname) throws NoSuchMethodException, SecurityException, NoSuchFieldException{
		return responseClass.getMethod("set"+fieldname.substring(0, 1).toUpperCase()+fieldname.substring(1), 
					responseClass.getDeclaredField(fieldname).getType());
		
	} 
	
	/********************************************************************************************************************/
	/*******************************************************************************************************************/
	/********************************************************************************************************************/
	public void putField(Method method, String fieldName, JsonElement jsonElement, Object obj,String jsonField)
			throws ReflectiveOperationException {

		Class<?> objClass = obj.getClass();
	

		if (!jsonElement.isJsonPrimitive() || fieldName==null) {
			
			if(jsonElement.isJsonArray()){
				System.out.println("SimpleResponseMappingConfig ~ 144 : not supported");// change here code to process
				// complex object
				return;
			}
			
			((SimpleResponseConfigUnit)mapConfig.get(jsonField))
											.putJsonInObject(jsonElement,obj);
			return;
		}
		/*******************************************************************/
		
		//System.out.println(objClass+"////"+fieldName);
		if(objClass.getDeclaredField(fieldName)==null)return;
		Class<?> fieldClass = objClass.getDeclaredField(fieldName).getType();
		JsonPrimitive jp = jsonElement.getAsJsonPrimitive();

		if (ConfigFactoryUtiles.isOfType(fieldClass, int.class, Integer.class)) {
			if (!jp.isNumber()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in integer filed " + fieldName);
				return;
			}
			method.invoke(obj, jp.getAsInt());
			return;

		}
		if (ConfigFactoryUtiles.isOfType(fieldClass, double.class, Double.class)) {
			if (!jp.isNumber()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in double filed " + fieldName);
				return;
			}
			method.invoke(obj, jp.getAsDouble());
			return;
		}

		if (fieldClass == String.class) {
			if (!jp.isString()) {
				System.out.println("exception SimpleResponseMappingConfig ~ can't put" + jsonElement
						+ " in String filed " + fieldName);
				return;
			}
			method.invoke(obj, jp.getAsString());
			return;
		}
		if (ConfigFactoryUtiles.isOfType(fieldClass, boolean.class, Boolean.class)) {
			if (!jp.isBoolean()) {
				System.out.println("exception");
				return;
			}
			method.invoke(obj, jp.getAsBoolean());
		}
	}
	@Override
	public String toString() {
		return "SimpleResponseMappingConfig [mapConfig=" + mapConfig + "]";
	}
	
	

}
