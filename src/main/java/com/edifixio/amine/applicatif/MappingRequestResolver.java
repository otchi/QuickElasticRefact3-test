package com.edifixio.amine.applicatif;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.utils.EntryImp;
import com.edifixio.amine.utils.JsonHandleUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class MappingRequestResolver {
	private Map<String,
				Entry<String,
					Entry<Integer,Integer>>> corresp; 



	private JsonObject  jsonObject;
	
	public MappingRequestResolver(JsonObject  jsonObject) {
		super();
		this.jsonObject=jsonObject;
		corresp=new HashMap<String, 
					Map.Entry<String,Entry<Integer,Integer>>>();
		
	}
/************************************************************************************************/	
/**********************************************************************************************/
	public Map<String, Entry<String, Entry<Integer, Integer>>> getCorresp() {
		return corresp;
	}
/*************************************************************************************************/
	public void resolverVars(){
		resolver(jsonObject, "");
	}
/**************************************************************************************************/	
	public void resolver(JsonElement jsonElement, String path){
		
		if(jsonElement.isJsonPrimitive()) {
			JsonPrimitive jsonPrimitive=jsonElement.getAsJsonPrimitive();
			
			if(jsonPrimitive.isString()){
				decoder(jsonPrimitive.getAsString(),path);
				return;
			}
			return;
		}
		
		if(jsonElement.isJsonObject()) {
			resolveInJsonObject(jsonElement.getAsJsonObject(), path);
			return;
		}
		
		if(jsonElement.isJsonArray()) {
			resolveInJsonArray(jsonElement.getAsJsonArray(), path);
			return;
		}
		
	}
/************************************************************************************************/
	public void resolveInJsonObject(JsonObject jsonObject, String path){
		Iterator<Entry<String, JsonElement>> jsoIter=
							jsonObject.entrySet().iterator();
		
		while(jsoIter.hasNext()){
			Entry<String, JsonElement> entry=jsoIter.next();
			if(!path.equals(""))
					path+=path+"::";
			
			resolver(entry.getValue(), path+entry.getKey());
		}
	}
/**********************************************************************************************/	
	public void resolveInJsonArray(JsonArray jsonArray, String path){
		for(int i=0;i<jsonArray.size();i++){
			if(!path.equals(""))
				path+=path+"::";
			resolver(jsonArray.get(i), path+i);
		}
	}
	
/*******************************************************************************************/	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void  decoder(String field,String path){
		
		int[][] index=indexer(field);
		for(int i=0;i<index.length;i++){
			
			String varName=field.substring(index[i][0]+2, index[i][1]-1);
			if(varName.length()==0){
				System.out.println("exception");
				return ;
			}
			
			if(corresp.containsKey(varName)){
				System.out.println("exception");
				return ;
			}
			
			corresp.put(varName,new EntryImp( path, 
										new EntryImp( index[i][0], 
												index[i][1])));
		}
	}
	
/**************************************************************************************/
	public int[][] indexer(String field){
		String[] strs=field.split("\\$");
		System.out.println(strs.length);
		if(strs.length<2)return null;
		int lastVariableIndex=-1, beginPos=0 ,endPos=0,indexOfVar=0;
		int[][] index=new int[strs.length-1][2];
		for(int i=0;i<strs.length;i++){
			beginPos=strs[i].indexOf('{');
			endPos=strs[i].indexOf('}');
			if(beginPos!=0||endPos==-1) continue;
			String var="$"+strs[i].substring(beginPos, endPos);
			indexOfVar=field.indexOf(var);
			if(indexOfVar<=lastVariableIndex){
				System.out.println("duplicated variable");
				return null;
			}
			lastVariableIndex=indexOfVar;
			
		}
		
		return null;
	}
	
/***************************************************************************************************/	
	public void replaceVar(String var,String value){
		Entry<String,Entry<Integer,Integer>> info=corresp.get(var);
		
		if(info==null){
			System.out.println("exception");
			return;
		}
		JsonParser jp=new JsonParser();
		String field=JsonHandleUtil.seletor(info.getKey(), jsonObject).getAsString();
		
		StringBuilder fieldStrBuild=new StringBuilder(field);
		fieldStrBuild.replace(info.getValue().getKey(), info.getValue().getValue(), value);
		
		int indexOfPrefix=info.getKey().lastIndexOf("::");
		if(indexOfPrefix<0)
			indexOfPrefix=0;
		
		JsonElement parent=JsonHandleUtil.seletor(info.getKey().substring(0, indexOfPrefix), jsonObject);
		
		if(parent.isJsonArray()){
			JsonArray ja=parent.getAsJsonArray();
			ja.remove(Integer.parseInt(info.getKey().substring(indexOfPrefix+2)));
			ja.add(jp.parse(fieldStrBuild.toString()));
		}
		if(parent.isJsonObject()){
			JsonObject jo=parent.getAsJsonObject();
			String elementName=info.getKey().substring(indexOfPrefix+2);
			jo.remove(elementName);
			jo.add(elementName,
					jp.parse(fieldStrBuild.toString()));
		}
		
	}
	
	public static void main(String args[]){
		String str="sss${bl}${bb}${km}";
		String[] s=str.split("\\$");
		System.out.println(s[1]);
	}

	
}
