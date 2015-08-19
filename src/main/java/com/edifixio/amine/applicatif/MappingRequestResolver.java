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

	public JsonObject getJsonObject() {
		return jsonObject;
	}
	
/**********************************************************************************************/
	public Map<String, Entry<String, Entry<Integer, Integer>>> getCorresp() {
		return corresp;
	}




/*************************************************************************************************/
	public void parsing(){
		parser(jsonObject, "");
	}
/**************************************************************************************************/	
	public void parser(JsonElement jsonElement, String path){
		
		if(jsonElement.isJsonPrimitive()) {
			JsonPrimitive jsonPrimitive=jsonElement.getAsJsonPrimitive();
			
			if(jsonPrimitive.isString()){
				stringParser(jsonPrimitive.getAsString(),path);
				return;
			}
			return;
		}
		
		if(jsonElement.isJsonObject()) {
			parseInJsonObject(jsonElement.getAsJsonObject(), path);
			return;
		}
		
		if(jsonElement.isJsonArray()) {
			parseInJsonArray(jsonElement.getAsJsonArray(), path);
			return;
		}
		
	}
/************************************************************************************************/
	public void parseInJsonObject(JsonObject jsonObject, String path){
		Iterator<Entry<String, JsonElement>> jsoIter=
							jsonObject.entrySet().iterator();
		Entry<String, JsonElement> entry;
		if(!path.equals(""))
			path=path+"::";
		while(jsoIter.hasNext()){
			entry=jsoIter.next();
			parser(entry.getValue(), path+entry.getKey());
		}
	}
/**********************************************************************************************/	
	public void parseInJsonArray(JsonArray jsonArray, String path){
		if(!path.equals(""))
			path=path+"::";
		for(int i=0;i<jsonArray.size();i++){
			parser(jsonArray.get(i), path+i);
		}
	}
	
/*******************************************************************************************/	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void stringParser(String field,String path){
		
		int[][] index=indexer(field);
		if(index==null) return;
		
		for(int i=0;i<index.length;i++){
			
			String varName=field.substring(index[i][0]+2, index[i][1]-1);
			if(varName.length()==0){
				System.out.println("MappingRequestResolver ~ exception 98");
				return ;
			}
			
			if(corresp.containsKey(varName)){
				System.out.println("MappingRequestResolver ~ exception 103");
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
		if(strs==null) return null;
		//System.out.println(strs.length);
		if(strs.length<2)return null;
		
		int lastVariableIndex=-1, beginPos=0 ,endPos=0,indexOfVar=0,j=0;
		int[][] index=new int[strs.length-1][2];
		String var;
	
		for(int i=0;i<strs.length;i++){
			//System.out.println("-"+strs[i]+"-");
			beginPos=strs[i].indexOf('{');
			endPos=strs[i].indexOf('}')+1;
			//System.out.println(beginPos+"-|"+endPos);
			if(beginPos!=0||endPos<2) continue;
			//System.out.println(beginPos+"!-!"+endPos);
			var="$"+strs[i].substring(beginPos, endPos);
			indexOfVar=field.indexOf(var);
			//System.out.println(indexOfVar);
			if(indexOfVar<=lastVariableIndex){
				
				System.out.println("\n duplicated variable");
				System.out.println("_______________________");
				return null;
			}
			//System.out.println(indexOfVar+"--|"+lastVariableIndex);
			lastVariableIndex=indexOfVar;
			index[j][0]=field.indexOf(var);
			index[j][1]=index[j][0]+var.length();
			//System.out.println(field.substring(index[j][0], index[j][1]));
			j++;
		}
		if(j==0) return null;
		
		return index;
	}
	
/***************************************************************************************************/	
	public void varResolver(String var,String value){
		Entry<String,Entry<Integer,Integer>> info=corresp.get(var);
			
		if(info==null){
			System.out.println("MappingRequestResolver ~ exception 162");
			return;
		}
		JsonParser jp=new JsonParser();
		String field=JsonHandleUtil.seletor(info.getKey(), jsonObject).getAsString();
		
		if(field==null){
			System.out.println("MappingRequestResolver ~ exception 173");
			return;
		}
		
		StringBuilder fieldStrBuild=new StringBuilder(field);
		fieldStrBuild.replace(info.getValue().getKey(), info.getValue().getValue(), value);
		
		int indexOfPrefix=info.getKey().lastIndexOf("::");
		if(indexOfPrefix<0)
			indexOfPrefix=0;
		
		JsonElement parent=
				JsonHandleUtil.seletor(info.getKey().substring(0,indexOfPrefix),
										jsonObject);
		
		if(indexOfPrefix!=0)indexOfPrefix+=2;
		if(parent.isJsonArray()){
			JsonArray ja=parent.getAsJsonArray();
			System.out.println(info+"!!"+(indexOfPrefix));
			ja.remove(Integer.parseInt(info.getKey().substring(indexOfPrefix)));
			ja.add(jp.parse("[\""+fieldStrBuild.toString()+"\"]").getAsJsonArray().get(0));
		}
		if(parent.isJsonObject()){
			JsonObject jo=parent.getAsJsonObject();
			
			String elementName=info.getKey().substring(indexOfPrefix);
			jo.remove(elementName);
			
			jo.add(elementName,//like that because it cause error in parsing JSON if
					//you pass a only string with space , same case for object
					jp.parse("[\""+fieldStrBuild.toString()+"\"]").getAsJsonArray().get(0));
		}
		corresp.remove(var);
		
	}

}
