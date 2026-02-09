package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.exception.AccountDetailsValidation;
import com.example.demo.mapper.AccountBalanceMapper;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDetailsValidation adv;
	
	
	@Override
	public void createAccount(Account account) {
		accountRepository.save(account);		
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account closeAccount(Long acno) {
				
		Account temp=this.getByAccountNumber(acno);
		accountRepository.deleteById(acno);
		return temp;
	}

	@Override
	public Account getByAccountNumber(Long acno) {
		adv.validateAccountNumber(acno);
		return accountRepository.findById(acno).orElseThrow(()-> new RuntimeException("Account number not found!!"));
	}

	@Override
	public Account getByEmail(String email) {
		adv.validateEmail(email);
		return accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found !!"));
	}

	@Override
	public Account getByMobile(String mobile) {		
		adv.validateMobileNumber(mobile);
		return accountRepository.findByMobile(mobile).orElseThrow(()-> new RuntimeException("Mobile number not found !!"));
	}

	@Override
	public BalanceDTO getBalance(Long acno) {
		   Account account =this.getByAccountNumber(acno);
	       return AccountBalanceMapper.toBalanceDTO(account); 
	}

	@Override
	public Account update(Long acno, UpdateAccountDTO dto) {
		
		Account existingAccount =this.getByAccountNumber(acno);
		
		if (dto.getName() != null)
	        existingAccount.setName(dto.getName());

	    if (dto.getEmail() != null) {
	    	adv.validateEmail(dto.getEmail());
	    	existingAccount.setEmail(dto.getEmail());
	    }

	    if (dto.getMob() != null) {
	    	adv.validateMobileNumber(dto.getMob());
	    	existingAccount.setMob(dto.getMob());
	    }
	        

	    if (dto.getAddress() != null)
	        existingAccount.setAddress(dto.getAddress());
		
		return accountRepository.save(existingAccount);
	}

	@Override
	public BalanceDTO withdrawAmount(Long acno, Double amount) {
		Account account = this.getByAccountNumber(acno);
		account.setBalance(account.getBalance()-amount);
		return AccountBalanceMapper.toBalanceDTO(account);
	}

	@Override
	public BalanceDTO depositAmount(Long acno, Double amount) {
		Account account = this.getByAccountNumber(acno);
		account.setBalance(account.getBalance()+amount);
		return AccountBalanceMapper.toBalanceDTO(account);
	}
}
