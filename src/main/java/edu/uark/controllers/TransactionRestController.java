package edu.uark.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import edu.uark.commands.transactions.TransactionNumberQuery;
import edu.uark.commands.transactions.TransactionQuery;
import edu.uark.commands.transactions.TransactionUpdateCommand;
import edu.uark.commands.transactions.TransactionsQuery;
import edu.uark.models.api.Transaction;
import edu.uark.models.api.TransactionListing;


//-----------Transaction-------------//
@RestController
@RequestMapping(value = "/transaction")
public class TransactionRestController 
{
	@RequestMapping(value = "/apiv0/{transactionId}", method = RequestMethod.POST)
	public Transaction getTransaction(@PathVariable UUID transactionId) 
	{
		return (new TransactionQuery()).
			setTransactionId(transactionId).
			execute();
	}

	@RequestMapping(value = "/apiv0/byTotal_Amt/{Total_Amt}", method = RequestMethod.POST)
	public Transaction getTransactionByTransaction_Num(@PathVariable String transactionTotal_Amt) 
	{
		return (new TransactionNumberQuery()).
			setTotalAmt(transactionTotal_Amt).
			execute();
	}

	@RequestMapping(value = "/apiv0/transactions", method = RequestMethod.POST)
	public TransactionListing getTransactions() 
	{
		return (new TransactionsQuery()).execute();
	}
	
	@RequestMapping(value = "/apiv0/", method = RequestMethod.PUT)
	public Transaction putTransaction(@RequestBody Transaction transaction) 
	{
		return (new TransactionUpdateCommand()).
			setApiTransaction(transaction).
			execute();
	}

	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() 
	{
		return "Successful test. (TransactionRestController)";
	}
}