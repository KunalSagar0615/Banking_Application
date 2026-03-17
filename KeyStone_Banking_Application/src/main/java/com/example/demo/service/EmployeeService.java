package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.TransactionsDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.enumeration.TransactionType;
import com.example.demo.model.Account;
import com.example.demo.model.Transactions;

public interface EmployeeService {

	//create
	void createAccount(Account account);
	
	//display
	List<AccountResponseDTO> getAllAccounts();
	List<AccountResponseDTO> getAllSavingAccounts();
	List<AccountResponseDTO> getAllCurrentAccounts();
	
	//delete
	AccountResponseDTO closeAccount(Long acno);
	
	//searching
	Account getByAccountNumber(Long acno);
	Account getByEmail(String email);
	Account getByMobile(String mobile);
	
	
	//check balance
	BalanceDTO getBalance(Long acno);
	
	//update Account 
	UpdateAccountDTO update(Long acno,UpdateAccountDTO updateAccountDto);
	
	//withdraw amount
	BalanceDTO withdrawAmount(Long acno,Double amount);
	
	//deposit amount
	BalanceDTO depositAmount(Long acno,Double amount);
	
	//add transactions into transaction table
	void setTransaction(Long acccountno,Double amount,TransactionType transactionType);

	
	//display account Transactions
	List<TransactionsDTO> getTransactionsByAccNo(Long accno);
	
	BalanceDTO transferMoney(Long fromAccount, Long toAccount, Double amount);
	
	
}
