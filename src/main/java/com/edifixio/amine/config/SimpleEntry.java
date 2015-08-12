package com.edifixio.amine.config;

import java.util.Map.Entry;

public class SimpleEntry<K,V> implements Entry<K, V> {
	K key;
	V value;
	
	public SimpleEntry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	public V getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public V setValue(V value) {
		// TODO Auto-generated method stub
		this.value=value;
		return value;
	}

}
