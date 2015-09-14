package com.edifixio.amine.application;

import java.lang.reflect.Method;
import java.util.Map;

import com.edifixio.amine.config.JsonBooleanConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;
import com.google.gson.JsonObject;

public class SimpleResponseConfigUnit extends SimpleResponseMappingConfig{
	private static final String NAME="name";
	private static final String MAPPING="mapping";
	private static final String LAZY="lazy";
	public SimpleResponseConfigUnit(Map<String, JsonElementConfig> mapConfig) {
		super(mapConfig);
	}
	
	
	public void putJsonInObject(JsonObject jsonObject, Object object) throws ReflectiveOperationException {
		
		String fieldName=((JsonStringConfig)this.mapConfig.get(NAME)).getValue();
		Method method=SimpleResponseMappingConfig.getSetter(object.getClass(), fieldName);
		Class<?> classOfField=object.getClass().getDeclaredField(fieldName).getType();
		//System.out.println(classOfField+"///"+object+"//"+jsonObject);
		Boolean isNotLazy=(!this.mapConfig.containsKey(LAZY)	||	!((JsonBooleanConfig)this.mapConfig.get(LAZY)).getValue());
		
		if(	(this.mapConfig.containsKey(MAPPING))){	
			
			if(isNotLazy){
				System.out.println(method+"//--"+fieldName+"//--"+object);
				method.invoke(object,	((SimpleResponseMappingConfig)
						this.mapConfig.get(MAPPING)).getSourceObject(classOfField, jsonObject));
				return;
			}
			
			return;
		}
		System.out.println();
		if(isNotLazy){
			System.out.println(method+"//"+fieldName+"//"+object);
			//super.putField(method, fieldName, jsonObject, object, null);
		}
	}

}
