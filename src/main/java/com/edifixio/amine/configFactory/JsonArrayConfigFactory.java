package com.edifixio.amine.configFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonPrimitiveTypeConfig;
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
			JsonPrimitiveTypeConfig jsPrimitiveTypeConfig,
			JsonArrayConfigFactory jsonArrayConfigFactoryChild, 
			JsonObjectConfigFactory jsonObjectConfigFactoryChild,
			JsonPrimitiveConfigFactory jsonPrimitiveConfigFactoryChild) {

		super(jsPrimitiveTypeConfig);
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

/*******************************************************************************************************************************/
	@Override
	public JsonElementConfig getJsonElementConfig(JsonElement jsonElement)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		if (!jsonElement.isJsonArray()) {
			if(jsonElement.isJsonPrimitive()&&jsPrimitiveTypeConfig!=null)
				return new JsonPrimitiveConfigFactory(jsPrimitiveTypeConfig).getJsonElementConfig(jsonElement);
			else { System.out.println("exception");return null;}
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
										(this.jConfigFactory[1] != null)? (byte)1 : (byte)-1
										 			   :(jse.isJsonPrimitive())?
										 					  (this.jConfigFactory[2] != null)?(byte) 2 :(byte)-1 :(byte)-1;
			if(index>0)
				jsonArrayConfigResult.addJsonElementConfig(this	.jConfigFactory[index]
															.getJsonElementConfig(jse));
			else{ System.out.println("exception"); return null;}

		}
		return jsonArrayConfigResult;
	}

}
