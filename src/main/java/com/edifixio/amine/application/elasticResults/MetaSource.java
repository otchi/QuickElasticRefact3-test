package com.edifixio.amine.application.elasticResults;

public class MetaSource {
	
	private String index;
	private String type;
	private String id;
	private double score;

	public MetaSource(String index, String type, String id, double score) {
		super();
		this.index = index;
		this.type = type;
		this.id = id;
		this.score = score;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public double getScore() {
		return score;
	}

}
