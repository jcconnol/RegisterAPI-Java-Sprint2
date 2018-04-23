package edu.uark.models.api;

import java.util.LinkedList;
import java.util.List;

public class TransactionListing 
{
	private List<Transaction> transactions;
	public List<Transaction> getTransactions() 
	{
		return this.transactions;
	}
	public TransactionListing setTransactions(List<Transaction> transactions) 
	{
		this.transactions = transactions;
		return this;
	}
	public TransactionListing addTransaction(Transaction transaction) 
	{
		this.transactions.add(transaction);
		return this;
	}
	
	public TransactionListing() 
	{
		this.transactions = new LinkedList<Transaction>();
	}
}