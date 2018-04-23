package edu.uark.commands.transactions;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.commands.ResultCommandInterface;
import edu.uark.models.api.Transaction;
import edu.uark.models.api.enums.TransactionApiRequestStatus;
import edu.uark.models.entities.TransactionEntity;
import edu.uark.models.repositories.TransactionRepository;
import edu.uark.models.repositories.interfaces.TransactionRepositoryInterface;

public class TransactionUpdateCommand implements ResultCommandInterface<Transaction> 
{
	@Override
	public Transaction execute() 
	{
		if (StringUtils.isBlank(this.apiTransaction.getRecordID())) 
		
			return (new Transaction()).setApiRequestStatus(TransactionApiRequestStatus.INVALID_INPUT);
		return apiTransaction;
		}
		
		TransactionEntity transactionEntity = this.transactionRepository.get(this.apiTransaction.getId()); {
		if (transactionEntity != null) 
		{
			this.apiTransaction = transactionEntity.synchronize(this.apiTransaction);
		} 
		else 
		{
			transactionEntity = this.transactionRepository.byRecordID(this.apiTransaction.getRecordID());
			if (transactionEntity == null) 
			{
				transactionEntity = new TransactionEntity(this.apiTransaction);
			} 
			else 
			{
				//return (new Transaction()).setApiRequestStatus(TransactionApiRequestStatus.LOOKUP_CODE_ALREADY_EXISTS);
			}
		}
		
		transactionEntity.save();
		if ((new UUID(0, 0)).equals(this.apiTransaction.getId())) 
		{
			this.apiTransaction.setId(transactionEntity.getId());
		}
		
		//return this.apiTransaction;
	}

	//Properties
	private Transaction apiTransaction;
	public Transaction getApiTransaction() 
	{
		return this.apiTransaction;
	}
	public TransactionUpdateCommand setApiTransaction(Transaction apiTransaction) 
	{
		this.apiTransaction = apiTransaction;
		return this;
	}
	
	private TransactionRepositoryInterface transactionRepository;
	public TransactionRepositoryInterface getTransactionRepository() 
	{
		return this.transactionRepository;
	}
	public TransactionUpdateCommand setTransactionRepository(TransactionRepositoryInterface transactionRepository) 
	{
		this.transactionRepository = transactionRepository;
		return this;
	}
	
	public TransactionUpdateCommand() 
	{
		this.transactionRepository = new TransactionRepository();
	}
}
