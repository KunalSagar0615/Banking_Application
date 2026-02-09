package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.exception.AccountDetailsValidation;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.mapper.AccountBalanceMapper;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.repository.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDetailsValidation adv;
	
	@Autowired
	private SavingAccount sa;
	
	@Autowired
	private CurrentAccount ca;
	
	
	@Override
	public void createAccount(Account account) {
		adv.validName(account.getName());
		adv.validateEmail(account.getEmail());
		adv.validateMobileNumber(account.getMob());	
		adv.validAdhar(account.getAdharNo());
		
		if(account instanceof SavingAccount) {
			if(account.getBalance()<sa.getMinBalance())
				throw new InvalidAmountException("You should have to add at least "+sa.getMinBalance()+"!");
		}else {
			if(account.getBalance()<ca.getMinBalance())
				throw new InvalidAmountException("You should have to add at least "+ca.getMinBalance()+"!");
		}
		
		
		accountRepository.save(account);		
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account closeAccount(Long acno) {
				
		Account temp=this.getByAccountNumber(acno);

//		if(temp.getBalance()!=0) {
//			use sendgrid here to send remaining amount temp.getBalance()
//			temp.setBalance(0);
//			accountRepository.save(temp);
//		}					
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
		
		if (dto.getName() != null) {
			adv.validName(dto.getName());
			existingAccount.setName(dto.getName());
		}

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

	@Transactional
	@Override
	public BalanceDTO withdrawAmount(Long acno, Double amount) {
		
		Account account = this.getByAccountNumber(acno);
		adv.validateAmount(amount);		
		
		if(account instanceof SavingAccount) {
			if(account.getBalance()-amount < sa.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdrow"+(account.getBalance()-sa.getMinBalance()));
			}
			
			if(amount>sa.getWithdrawLimit()) {
				throw new InvalidAmountException("You cannot withdraw more than "+sa.getWithdrawLimit()+" at a time !");
			}
			
		}else {
			if(account.getBalance()-amount < ca.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdrow"+(account.getBalance()-ca.getMinBalance()));
			}
		}
		
		account.setBalance(account.getBalance()-amount);
		return AccountBalanceMapper.toBalanceDTO(account);
	}

	@Transactional
	@Override
	public BalanceDTO depositAmount(Long acno, Double amount) {
		
		adv.validateAmount(amount);
		
		Account account = this.getByAccountNumber(acno);
		account.setBalance(account.getBalance()+amount);
		return AccountBalanceMapper.toBalanceDTO(account);
	}
}
