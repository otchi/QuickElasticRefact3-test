package com.edifixio.amine.application.elasticResults;

import java.util.LinkedList;
import java.util.List;

public class RangeAggr extends  FacetableAggr{

	protected RangeAggr(List<Bucket> buckets) {
		super(buckets);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isTermAggr() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TermAggr getAsTermAggr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRangeAggr() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public RangeAggr getAsRangeAggr() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public List<RangeBucket> getRangeBuckets(){
		List<RangeBucket> rangeBuckets=new LinkedList<RangeBucket>();
		List<Bucket> buckets=this.getBuckets();
		for(int i=0;i<buckets.size();i++){
			rangeBuckets.add((RangeBucket) buckets.get(i));
		}
		return rangeBuckets;	
	}




}
