package com.ciandt.poc.beans;

import java.util.List;

public class ColumnFamily {
	
	private String name;
	private List<ColumnQualifiers> columnQualifiers;
	
	public ColumnFamily(){
		
	}
	
	public ColumnFamily(String name, List<ColumnQualifiers> columnQualifiers) {
		super();
		this.name = name;
		this.columnQualifiers = columnQualifiers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnQualifiers> getColumnQualifiers() {
		return columnQualifiers;
	}

	public void setColumnQualifiers(List<ColumnQualifiers> columnQualifiers) {
		this.columnQualifiers = columnQualifiers;
	}
}
