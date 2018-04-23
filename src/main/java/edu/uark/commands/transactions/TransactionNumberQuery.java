package edu.uark.commands.transactions;

import org.apache.commons.lang3.StringUtils;

import edu.uark.commands.ResultCommandInterface;
import edu.uark.models.api.Transaction;
import edu.uark.models.api.enums.TransactionApiRequestStatus;
import edu.uark.models.entities.TransactionEntity;
import edu.uark.models.repositories.TransactionRepository;
import edu.uark.models.repositories.interfaces.TransactionRepositoryInterface;

public class TransactionNumberQuery implements ResultCommandInterface<Transaction> 
{
	@Override
	public Transaction execute() 
	{
		if (StringUtils.isBlank(this.totalAmt)) 
		{
			return new Transaction().setApiRequestStatus(TransactionApiRequestStatus.INVALID_INPUT);
		}
		
		TransactionEntity transactionEntity = this.transactionRepository.byTotal_Amt(this.totalAmt);
		if (transactionEntity != null) 
		{
			return new Transaction();
		} 
		else 
		{
			return new Transaction().setApiRequestStatus(TransactionApiRequestStatus.NOT_FOUND);
		}
	}

	//Properties
	private String totalAmt;
	public String gettotal_Amt() 
	{
		return this.totalAmt;
	}
	public TransactionNumberQuery setTotalAmt(String total_Amt) 
	{
		this.totalAmt= total_Amt;
		return this;
	}
	
	private TransactionRepositoryInterface transactionRepository;
	public TransactionRepositoryInterface getTransactionRepository() 
	{
		return this.transactionRepository;
	}
	public TransactionNumberQuery setTransactionRepository(TransactionRepositoryInterface transactionRepository) 
	{
		this.transactionRepository = transactionRepository;
		return this;
	}
	
	public TransactionNumberQuery() 
	{
		this.transactionRepository = new TransactionRepository();
	}
}