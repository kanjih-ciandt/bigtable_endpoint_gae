package com.ciandt.poc.beans;

import java.util.List;

public class ElementBigtable {
	
	private String key;
	private List<ColumnFamily> columnFamily;
	
	public ElementBigtable() {
		
	}

	public ElementBigtable(String key, List<ColumnFamily> columnFamily) {
		super();
		this.key = key;
		this.columnFamily = columnFamily;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<ColumnFamily> getColumnFamily() {
		return columnFamily;
	}

	public void setColumnFamily(List<ColumnFamily> columnFamily) {
		this.columnFamily = columnFamily;
	}
	
	

}
