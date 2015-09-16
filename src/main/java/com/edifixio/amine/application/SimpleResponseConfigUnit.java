package com.edifixio.amine.application;

import java.lang.reflect.Method;
import java.util.Map;

import com.edifixio.amine.config.JsonBooleanConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.edifixio.amine.utils.JsonPathTree;
import com.google.gson.JsonElement;

public class SimpleResponseConfigUnit extends SimpleResponseMappingConfig{
	
	private static final String NAME="name";
	private static final String MAPPING="mapping";
	private static final String LAZY="lazy";
	private JsonPathTree jsonPathTree;
	public SimpleResponseConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	
	public void putJsonInObject(JsonElement jsonElement, Object object) throws ReflectiveOperationException {
		
		String fieldName=((JsonStringConfig)this.mapConfig.get(NAME)).getValue();
		Method method=SimpleResponseMappingConfig.getSetter(object.getClass(), fieldName);
		Class<?> classOfField=object.getClass().getDeclaredField(fieldName).getType();
		//System.out.println(classOfField+"///"+object+"//"+jsonObject);
		Boolean isLazy=(this.mapConfig.containsKey(LAZY)	&&	((JsonBooleanConfig)this.mapConfig.get(LAZY)).getValue());
		
		if(	(this.mapConfig.containsKey(MAPPING))){	
			
			if(!isLazy){
				//System.out.println(method+"//--"+fieldName+"//--"+object);
				method.invoke(object,((SimpleResponseMappingConfig)
						this.mapConfig.get(MAPPING)).getSourceObject(classOfField, jsonElement.getAsJsonObject()));
				return;
			}
			
			return;
		}
		
		if(!isLazy){
			//System.out.println(method+"//"+fieldName+"//"+object);
			super.putField(method, fieldName, jsonElement, object, null);
		}
	}
	
	public JsonPathTree getLazyTree(String name,String parentPath){
		
		JsonPathTree jsonPathTree;
		Boolean isLazy=(this.mapConfig.containsKey(LAZY)	&&	((JsonBooleanConfig)this.mapConfig.get(LAZY)).getValue());
		
		jsonPathTree=new JsonPathTree(name, parentPath, isLazy);
		
		if((this.mapConfig.containsKey(MAPPING))){
			super.lazyTreeProcess(jsonPathTree);
		}
		this.jsonPathTree=jsonPathTree;
		return this.jsonPathTree;
	}

}
