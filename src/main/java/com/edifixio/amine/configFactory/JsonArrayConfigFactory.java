package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.TypesJsonPrimitiveConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.google.gson.JsonElement;

public class JsonArrayConfigFactory extends JsonCompoundConfigFactory {

	private Class<? extends JsonArrayConfig> classToFactory;
	
	// in array we must indicate procedure if the term is array or object or primary 
	// if we ignore one of this  or chose a constructor who ignore its this
	// kind of element become prohibit in this array
	private JsonElementConfigFactory jConfigFactory[]=
			new JsonElementConfigFactory[3];
	//private Class<? extends json>
	
/******************************************************************************************************************************/
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			TypesJsonPrimitiveConfig typeJsonPrimitiveConfig,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild, 
			JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(typeJsonPrimitiveConfig);
		this.jConfigFactory[0]= jsonArrayConfigFactoryChild;
		this.jConfigFactory[1]= jsonObjectConfigFactoryChild;
		this.jConfigFactory[2] =jsonPrimitiveConfigFactoryChild;
		this.classToFactory = classToFactory;
	}
/********************************************************************************************************************************/	
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[0] = jsonArrayConfigFactoryChild;
	}
	
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonObjectConfigFactory jsonObjectConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[1]= jsonObjectConfigFactoryChild;
	}
	
	public JsonArrayConfigFactory(Class<? extends JsonArrayConfig> classToFactory,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {
		super();
		this.classToFactory = classToFactory;
		this.jConfigFactory[2] =jsonPrimitiveConfigFactoryChild;
	}

/**
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws QuickElasticException *****************************************************************************************************************************/
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement) throws 
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, QuickElasticException {
		// TODO Auto-generated method stub
		if (!jsonElement.isJsonArray()) {
			if(jsonElement.isJsonPrimitive()&&typeJsonPrimitiveConfig!=null)
				return new JsonPrimitiveConfigFactory(typeJsonPrimitiveConfig)
						.getJsonElementConfig(jsonElement);
			else { System.out.println("JsonArrayConfigFactory~exception 66");return null;}
		}

		JsonArrayConfig jsonArrayConfigResult = this.classToFactory	.getConstructor()
																	.newInstance();
		Iterator<JsonElement> jsonArrayIterator = jsonElement	.getAsJsonArray()
																.iterator();
		JsonElement jse;
		byte index=-1;
		
		
		while (jsonArrayIterator.hasNext()) {
			jse = jsonArrayIterator.next();
			
			index=(jse.isJsonArray())?
				(this.jConfigFactory[0] != null)? (byte)0 :(byte) -1 
								 	 : (jse.isJsonObject())? 
										(this.jConfigFactory[1] != null)? (byte)1 : (byte)-2
										 			   :(jse.isJsonPrimitive())?
										 					  (this.jConfigFactory[2] != null)?(byte) 2 :(byte)-3 :(byte)-4;
			if(index>=0){
				jsonArrayConfigResult.addJsonElementConfig(this	.jConfigFactory[index]
															.getJsonElementConfig(jse));
			}else{ 
				
				if(index==-1) throw new QuickElasticException("json array not supported as child");
				if(index==-2) throw new QuickElasticException("json object not supported as child");
				if(index==-3) throw new QuickElasticException("json premitive not supported as child");
				
				throw new QuickElasticException("no defined exception provoqued by JsonArrayConfigFactory");
			}

		}
		return jsonArrayConfigResult;
	}

}
