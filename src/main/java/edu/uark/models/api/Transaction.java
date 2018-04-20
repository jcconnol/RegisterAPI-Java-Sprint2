package edu.uark.models.api;

import java.util.UUID;

public class Transaction {	
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Transaction setId(UUID id) {
		this.id = id;
		return this;
	}
	
	private String recordID;
	public String getRecordID() {
		return this.recordID;
	}
	public Transaction setRecordID(String recordID) {
		this.recordID = recordID;
		return this;
	}
	
	private String employeeID;
	public String getEmployeeID() {
		return this.employeeID;
	}
	public Transaction setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
		return this;
	}
	
	private int totalAmt;
	public int getTotalAmt() {
		return this.totalAmt;
	}
	public Transaction setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
		return this;
	}
	
	private String type;
	public String getType() {
		return this.type;
	}
	public Transaction setType(String type) {
		this.type = type;
		return this;
	}
	
	private String refID;
	public String getRefID() {
		return this.refID;
	}
	public Transaction setRefID(String refID) {
		this.refID = refID;
		return this;
	}
	
	public Transaction() {
		this.id = new UUID(0, 0);
		this.employeeID = "";
		this.totalAmt = 0;
		this.type = "s";
		this.recordID = "";
		this.refID = "";
	}
	
	public Transaction(Transaction transactionEntity) {
		this.id = transactionEntity.getId();
		this.employeeID = transactionEntity.getEmployeeID();
		this.totalAmt = transactionEntity.getTotalAmt();
		this.type = transactionEntity.getType();
		this.recordID = transactionEntity.getRecordID();
		this.refID = transactionEntity.getRefID();
	}
}
