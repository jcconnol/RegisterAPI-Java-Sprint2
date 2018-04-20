package edu.uark.models.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.uark.dataaccess.entities.BaseEntity;
import edu.uark.dataaccess.repository.DatabaseTable;
import edu.uark.models.api.Transaction;
import edu.uark.models.entities.fieldnames.ProductFieldNames;
import edu.uark.models.entities.fieldnames.TransactionFieldNames;

public class TransactionEntity extends BaseEntity<TransactionEntity>{
	
	@Override
	protected void fillFromRecord(ResultSet rs) throws SQLException {
		this.recordID = rs.getString(TransactionFieldNames.RECORD_ID);
		this.totalAmt = rs.getInt(TransactionFieldNames.TOTAL_AMT);
	}

	@Override
	protected Map<String, Object> fillRecord(Map<String, Object> record) {
		record.put(TransactionFieldNames.RECORD_ID, this.recordID);
		record.put(TransactionFieldNames.TOTAL_AMT, this.totalAmt);
		
		return record;
	}

	private String recordID;
	public String getRecordID() {
		return this.recordID;
	}
	public TransactionEntity setRecordID(String recordID) {
		if (!StringUtils.equals(this.recordID, recordID)) {
			this.recordID = recordID;
			this.propertyChanged(ProductFieldNames.LOOKUP_CODE);
		}
		
		return this;
	}

	private int totalAmt;
	public int getTotalAmt() {
		return this.totalAmt;
	}
	public TransactionEntity setTotalAmt(int totalAmt) {
		if (this.totalAmt != totalAmt) {
			this.totalAmt = totalAmt;
			this.propertyChanged(TransactionFieldNames.TOTAL_AMT);
		}
		
		return this;
	}
	
	public Transaction synchronize(Transaction apiTransaction) {
		this.setTotalAmt(apiTransaction.getTotalAmt());
		this.setRecordID(apiTransaction.getRecordID());
		
		apiTransaction.setId(this.getId());
		apiTransaction.setTotalAmt(this.getTotalAmt());
		
		return apiTransaction;
	}
	
	public TransactionEntity() {
		super(DatabaseTable.TRANSACTION);
		
		this.totalAmt = -1;
		this.recordID = StringUtils.EMPTY;
	}
	
	public TransactionEntity(Transaction apiTransaction) {
		super(DatabaseTable.TRANSACTION);
		
		this.totalAmt = apiTransaction.getTotalAmt();
		this.recordID = apiTransaction.getRecordID();
	}
}
