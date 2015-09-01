package com.edifixio.amine.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public abstract class JsonHandleUtil {
	
	private static final JsonParser JP=new JsonParser();

	/***********************************************************************************/
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
			/*************************************************/
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
		}
		
		return jse;

	}
	
	/*******************************************************************************************/
	public static JsonArray replaceInJsonArray(JsonArray jsonArray,int indiceToReplace,JsonElement replacement){
		
		List<JsonElement> list=new LinkedList<JsonElement>();
		
		for(int i=indiceToReplace+1;i<jsonArray.size();i++)
			list.add(jsonArray.remove(indiceToReplace+1));
		
		jsonArray.remove(indiceToReplace);
		jsonArray.add(replacement);
	
		for(JsonElement je:list)
			jsonArray.add(je);
		
		
		return jsonArray;	
	}
	
	/*************************************************************************/
	public static JsonElement jsonFile(String path) throws IOException{
		String stringResult="",inter;
		BufferedReader br=new BufferedReader(
							new FileReader(new File(path)));
		
		while((inter=br.readLine())!=null)
			stringResult+=inter;
			
		br.close();
		return JP.parse(stringResult);
	}
	/******************************************************************************/
	public static JsonElement jsonString(String jsonStr) {
		return JP.parse(jsonStr);
	}
	
	
	public static void main(String args[]){	
	}

	
	
	

}
