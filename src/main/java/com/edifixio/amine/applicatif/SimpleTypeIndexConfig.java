package com.edifixio.amine.applicatif;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.edifixio.amine.config.JsonArrayConfig;
import com.edifixio.amine.config.JsonElementConfig;
import com.edifixio.amine.config.JsonStringConfig;

public class SimpleTypeIndexConfig extends JsonArrayConfig {
	public List<String> getJsonStringConfigs(){
		List<String> result=new LinkedList<String>();
		Iterator<JsonElementConfig> types=this.jsonElementConfigs.iterator();
		
		while(types.hasNext()){
			result.add(((JsonStringConfig)types).getValue());
		}
		return result;
	}
}
