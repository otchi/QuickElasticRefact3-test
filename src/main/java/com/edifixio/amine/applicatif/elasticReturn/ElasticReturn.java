package com.edifixio.amine.applicatif.elasticReturn;

import java.util.List;

import io.searchbox.core.search.aggregation.Aggregation;

public class ElasticReturn {
	
	private ReturnMetas returnMetas;
	private List<Source> sources;
	private Aggregation aggregation;
	
	public ReturnMetas getReturnMetas() {
		return returnMetas;
	}
	public List<Source> getSources() {
		return sources;
	}
	public Aggregation getAggregation() {
		return aggregation;
	}
	

}
