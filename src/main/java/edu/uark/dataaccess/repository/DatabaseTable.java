package edu.uark.dataaccess.repository;

public enum DatabaseTable {
	NONE(""),
	PRODUCT("product"),
	EMPLOYEE("employee"),
	TRANSACTION("transaction");
	
	public String getLabel() {
		return label;
	}
	
	private final String label;
	
	private DatabaseTable(String label) {
		this.label = label;
	}
}
