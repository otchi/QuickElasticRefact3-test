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
	private String VarRegx="\\$\\{[a-z]*\\}"; 
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
	public MappingRequestResolver(String VarRegx,JsonObject  jsonObject) {
		this(jsonObject);
		this.VarRegx = VarRegx;
	}
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
		
		String[] strs=field.split(VarRegx);
		for(int i=0;i<strs.length;i++){
			//System.out.println(strs[i]);
		}
		/*****************************************************************************************/
		System.out.println("--"+strs.length);
		if( (strs.length == 0) && ( field.length() != 0) ){
			//System.out.println("ret");
			if(field.indexOf('}')!=field.length()-1){
				//System.out.println("bet");
				strs=field.split("\\$\\{");
				//System.out.println("++"+strs.length);
				StringBuilder varStrBuild=new StringBuilder(field);
				int lenghtOfRemoved=0;
				int[][] response=new int[strs.length-1][2];
				int i=0;
				//System.out.println(varStrBuild.length()!=0&&i<strs.length);
				while(varStrBuild.length()!=0&&i<strs.length-1){
					//System.out.println(varStrBuild);
					int endVar=varStrBuild.indexOf("}")+1;
					varStrBuild.delete(0, endVar);
					response[i][0]=lenghtOfRemoved;
					lenghtOfRemoved+=endVar;
					response[i][1]=lenghtOfRemoved;
					//System.out.println(field.substring(response[i][0],response[i][1]));
					i++;
					
				}
				return response;
			}
			
			int[][] response=new int[1][2];
			response[0][0]=0;
			response[0][1]=field.length();
			
			//System.out.println(response[0][0]+"-"+response[0][1]);
			//System.out.println(field.substring(response[0][0],response[0][1]));
			return response;
		}
		/*********************************************************************************************/
		if( (strs.length == 1) && (strs[0].length() != field.length()) ){
			int[][] response=new int[1][2];
			response[0][0]=strs[0].length();
			response[0][1]=field.length();
			//System.out.println(response[0][0]+"-"+response[0][1]);
			//System.out.println(var.substring(response[0][0],response[0][1]));
			return response;
		}
		/*******************************************************************************************/
		if(strs.length>1){
			StringBuilder varStrBuild=new StringBuilder(field);
			int[][] index=new int[strs.length][2];
			int lenghtOfRemoved=0;
			int endOfStr;
			/***************************************************************/
			for(int i=0;i<strs.length;i++){
				index[i][0]=varStrBuild.indexOf(strs[i])+lenghtOfRemoved;
				endOfStr= varStrBuild.indexOf(strs[i])+strs[i].length();
				index[i][1]=endOfStr+lenghtOfRemoved;
				lenghtOfRemoved+=endOfStr;
				varStrBuild.delete(0,endOfStr);
				//System.out.println(index[i][0]+"-*"+index[i][1]);
			}
			/*************************************************************/
			int[][] result=
			(index[strs.length-1][1]==field.length())?
								new int[strs.length-1][2]
												   :
								new int[strs.length][2];
			
			int i=0;
			
			for(i=1;i<strs.length;i++){
				result[i-1][0]=index[i-1][1];
				result[i-1][1]=index[i][0];
				//System.out.println(result[i-1][0]+"-"+result[i-1][1]);
			}
			//System.out.println(index[strs.length-1][1]+"**"+var.length());
			if(index[strs.length-1][1]<field.length()){
				//System.out.println("ccd");
				result[i-1][0]=index[i-1][1];
				//System.out.println("--"+index[i-1][1]);
				result[i-1][1]=field.length();
				//System.out.println(result[i-1][0]+"!-"+result[i-1][1]);
			}
			
			return result;
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
		StringBuilder sb=new StringBuilder(str);
		StringBuilder newSb=new StringBuilder();
		while(sb.length()!=0){
		int postion=sb.indexOf("}")+1;
		newSb.append(sb.subSequence(0, postion)+" ");
		sb.delete(0, postion);
		System.out.println(newSb);
		}
		
		//newSb.toString().split(regex);
	}

	
}
