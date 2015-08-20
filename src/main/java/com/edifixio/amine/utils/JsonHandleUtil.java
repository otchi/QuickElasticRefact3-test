package com.edifixio.amine.utils;


import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class JsonHandleUtil {

	public static JsonElement seletor(String selecor, JsonElement jse) {
		if(selecor==null) return jse;
		String[] ArraySelecor=selecor.split("::");
		if (!ArraySelecor[0].equals("")) {
			JsonElement inter = null;
			if (jse.isJsonObject())
				inter = jse.getAsJsonObject();
			if (jse.isJsonArray())
				inter = jse.getAsJsonArray();
			if (jse.isJsonPrimitive())
				return null;

			int i = 0;

			while ((inter != null) && i < ArraySelecor.length) {
				if (inter.isJsonObject())
					inter = inter.getAsJsonObject().get(ArraySelecor[i]);
				else if (inter.isJsonArray())
					inter = inter.getAsJsonArray().get(Integer.parseInt(ArraySelecor[i]));
				else if (inter.isJsonPrimitive())
					return null;
				i++;
			}

			return inter;
		} else
			return jse;

	}
	
	public static JsonArray replaceInJsonArray(
			JsonArray jsonArray,int indiceToReplace,JsonElement replacement){
		List<JsonElement> list=new LinkedList<JsonElement>();
		for(int i=indiceToReplace+1;i<jsonArray.size();i++){
			list.add(jsonArray.remove(indiceToReplace+1));
		}
		jsonArray.remove(indiceToReplace);
		jsonArray.add(replacement);
		
		for(JsonElement je:list){
			jsonArray.add(je);
		}
		return jsonArray;	
	}
	
	
	public static void main(String args[]){	
	}

	
	
	

}
