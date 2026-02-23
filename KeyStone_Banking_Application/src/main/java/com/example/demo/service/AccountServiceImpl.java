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
import com.example.demo.repository.CurrentAccountRepository;
import com.example.demo.repository.SavingAccountRepository;


@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDetailsValidation adv;
	
	@Autowired
	private SavingAccountRepository savingAccountRepository;
	
	@Autowired
	private CurrentAccountRepository currentAccountRepository;
		
	@Override
	public void createAccount(Account account) {
		
		adv.validName(account.getName());
		adv.validateEmail(account.getEmail());
		adv.validateMobileNumber(account.getMob());	
		adv.validAdhar(account.getAdharNo());
		
		if(account instanceof SavingAccount) {
			
			SavingAccount savingAccount = (SavingAccount) account;
			
			if(account.getBalance()<savingAccount.getMinBalance())
				throw new InvalidAmountException("You should have to add at least "+savingAccount.getMinBalance()+"!");
//			else
//				savingAccountRepository.save(savingAccount);
//			
		}else if(account instanceof CurrentAccount){
			
			CurrentAccount currentAccount=(CurrentAccount) account;
			
			if(account.getBalance()<currentAccount.getMinBalance())
				throw new InvalidAmountException("You should have to add at least "+currentAccount.getMinBalance()+"!");
//			else 
//				currentAccountRepository.save(currentAccount);
		
		}	
		
		accountRepository.save(account);
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Transactional
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
	public Account getByMobile(String mob) {		
		adv.validateMobileNumber(mob);
		return accountRepository.findByMob(mob).orElseThrow(()-> new RuntimeException("Mobile number not found !!"));
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
		
		if(account instanceof SavingAccount savingAccount) {
			if(account.getBalance()-amount < savingAccount.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdrow"+(account.getBalance()-savingAccount.getMinBalance()));
			}
			
			if(amount>savingAccount.getWithdrawLimit()) {
				throw new InvalidAmountException("You cannot withdraw more than "+savingAccount.getWithdrawLimit()+" at a time !");
			}
			
		}else if(account instanceof CurrentAccount currentAccount){
			if(account.getBalance()-amount < currentAccount.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdrow"+(account.getBalance()-currentAccount.getMinBalance()));
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
