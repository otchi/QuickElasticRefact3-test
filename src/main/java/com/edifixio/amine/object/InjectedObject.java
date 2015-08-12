package com.edifixio.amine.object;

import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;

public class InjectedObject {
	
	private JestClient jestClient;
	private JestResult jestResult;
	private List<Object> result;
	

	public JestClient getJestClient() {
		return jestClient;
	}
	public void setJestClient(JestClient jestClient) {
		this.jestClient = jestClient;
	}
	public JestResult getJestResult() {
		return jestResult;
	}
	public void setJestResult(JestResult jestResult) {
		this.jestResult = jestResult;
	}
	public List<Object> getResult() {
		return result;
	}
	public void setResult(List<Object> result) {
		this.result = result;
	}

}
