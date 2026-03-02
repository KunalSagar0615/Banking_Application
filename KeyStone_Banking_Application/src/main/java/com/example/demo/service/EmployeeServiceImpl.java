package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.BalanceDTO;
import com.example.demo.dto.TransactionsDTO;
import com.example.demo.dto.UpdateAccountDTO;
import com.example.demo.email.EmailService;
import com.example.demo.enumeration.TransactionType;
import com.example.demo.exception.AccountDetailsValidation;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.exception.InvalidEmailFormate;
import com.example.demo.exception.InvalidMobileNumber;
import com.example.demo.mapper.AccountBalanceMapper;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.model.Transactions;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CurrentAccountRepository;
import com.example.demo.repository.SavingAccountRepository;
import com.example.demo.repository.TransactionRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDetailsValidation adv;
	
	@Autowired
	private SavingAccountRepository savingAccountRepository;
	
	@Autowired
	private CurrentAccountRepository currentAccountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EmailService emailService;
	
	
//-----------------------------CREATE ACCOUNT--------------------------------------	
		
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

		}else if(account instanceof CurrentAccount){
			
			CurrentAccount currentAccount=(CurrentAccount) account;
			
			if(account.getBalance()<currentAccount.getMinBalance())
				throw new InvalidAmountException("You should have to add at least "+currentAccount.getMinBalance()+"!");

		}	
		
		accountRepository.save(account);
	}

	
	
	
// -----------------------------------DISPLAY--------------------------------------------------
	@Override
	public List<AccountResponseDTO> getAllAccounts() {
		return accountRepository.findAll().stream().map(AccountResponseDTO :: toAccountResponseDTO).toList(); 	
	}
	
	@Override
	public List<AccountResponseDTO> getAllSavingAccounts() {
		return savingAccountRepository.findAll().stream().map(AccountResponseDTO :: toAccountResponseDTO).toList();
	}

	@Override
	public List<AccountResponseDTO> getAllCurrentAccounts() {
		return currentAccountRepository.findAll().stream().map(AccountResponseDTO :: toAccountResponseDTO).toList();
	}
	
	

//--------------------------------------------------------DISPLAY TRANSACTIONS BY ACCOUNT NUMBER--------------------------------------------------------------------
	@Override
	public List<TransactionsDTO> getTransactionsByAccNo(Long accno) {

	    Account account=this.getByAccountNumber(accno);
	    
	    List<Transactions> transactions =transactionRepository.findByAccountno(accno);
	    if (transactions.isEmpty()) {
	        throw new AccountNotFoundException("Transactions not found with Account number: " + accno);
	    }
	    
	    return transactions.stream().map(TransactionsDTO::toTransactionsDTO).toList();
	}
	
	
	
//--------------------------------------------------------DISPLAY BALANCE--------------------------------------------------------------------
	@Override
	public BalanceDTO getBalance(Long acno) {
		Account account =this.getByAccountNumber(acno);
		return AccountBalanceMapper.toBalanceDTO(account); 
	}
	
	
	
	
//--------------------------------------------------------CLOSE ACCOUNT--------------------------------------------------------------------
	@Transactional
	@Override
	public AccountResponseDTO closeAccount(Long acno) {
				
		Account temp=accountRepository.findById(acno).orElseThrow(()-> new AccountNotFoundException("Account not found with account number: " + acno));
		Double remainingBalance = temp.getBalance();
		
		String subject = "Account Closed Successfully";
		String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Account Closed</title></head><body style=\"font-family:Arial;background:#f4f6f8;padding:20px;\"><div style=\"max-width:600px;margin:auto;background:white;padding:30px;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.1);\"><h2 style=\"color:#d32f2f;\">Account Closed Successfully</h2><p>Dear Customer,</p><p>Your account has been successfully closed.</p><p><strong>Remaining Amount Withdrawn:</strong> ₹" + remainingBalance + "</p><p>If you did not request this action, please contact our support immediately.</p><br><p>Thank you for banking with us.<br><strong>Keystone Banking Team</strong></p></div></body></html>";
		emailService.sendMail(temp.getEmail(), subject, message);

	    //delete account
	    accountRepository.deleteById(acno);
		return AccountResponseDTO.toAccountResponseDTO(temp);
	}

	
	
//--------------------------------------------------------SEARCH BY ACCOUNT NUMBER--------------------------------------------------------------------
	@Override
	public Account getByAccountNumber(Long acno) {
		adv.validateAccountNumber(acno);
		return accountRepository.findById(acno).orElseThrow(()-> new AccountNotFoundException("Account not found with account number: " + acno));
	}


	
	
//--------------------------------------------------------SEARCH BY EMAIL--------------------------------------------------------------------
	@Override
	public Account getByEmail(String email) {
		adv.validateEmail(email);
		return accountRepository.findByEmail(email).orElseThrow(() -> new InvalidEmailFormate("Email not found !!"));
	}


	
	
//--------------------------------------------------------SEARCH BY MOBILE NUMBER--------------------------------------------------------------------
	@Override
	public Account getByMobile(String mob) {		
		adv.validateMobileNumber(mob);
		return accountRepository.findByMob(mob).orElseThrow(()-> new InvalidMobileNumber("Mobile number not found !!"));
	}




	

//--------------------------------------------------------UPDATE ACCOUNT DETAILS--------------------------------------------------------------------
	@Override
	public UpdateAccountDTO update(Long acno, UpdateAccountDTO dto) {
		
		Account existingAccount=this.getByAccountNumber(acno);
		
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
		
		
		return UpdateAccountDTO.toAccountDTO(existingAccount);
	}

	

//--------------------------------------------------------WITHDRAW AMOUNT--------------------------------------------------------------------
	@Transactional
	@Override
	public BalanceDTO withdrawAmount(Long acno, Double amount) {
		
		Account account = this.getByAccountNumber(acno);
		adv.validateAmount(amount);		
		
		if(account instanceof SavingAccount savingAccount) {
			if(account.getBalance()-amount < savingAccount.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdraw "+(account.getBalance()-savingAccount.getMinBalance()));
			}
			
			if(amount>savingAccount.getWithdrawLimit()) {
				throw new InvalidAmountException("You cannot withdraw more than "+savingAccount.getWithdrawLimit()+" at a time !");
			}
			
		}else if(account instanceof CurrentAccount currentAccount){
			if(account.getBalance()-amount < currentAccount.getMinBalance()) {
				throw new InvalidAmountException("You should have to maintain minimum balance!! You can only withdraw "+(account.getBalance()-currentAccount.getMinBalance()));
			}
		}
		
		account.setBalance(account.getBalance()-amount);
		
		setTransaction(acno, amount, TransactionType.DEBIT);
		
		String subject = "Withdrawal Successful";
		String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Withdrawal Successful</title></head><body style=\"margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f6f8;\"><table align=\"center\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#ffffff; margin-top:30px; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);\"><tr><td style=\"background-color:#d32f2f; padding:20px; text-align:center; color:white;\"><h2 style=\"margin:0;\">Keystone Banking</h2></td></tr><tr><td style=\"padding:30px;\"><h3 style=\"color:#333;\">Withdrawal Successful ✅</h3><p style=\"font-size:15px; color:#555;\">Dear Customer,</p><p style=\"font-size:15px; color:#555;\">The following amount has been successfully debited from your account:</p><div style=\"background:#fdecea; padding:15px; border-radius:6px; margin:20px 0;\"><p style=\"margin:5px 0; font-size:16px;\"><strong>Withdrawn Amount:</strong> ₹" + amount + "</p><p style=\"margin:5px 0; font-size:16px;\"><strong>Available Balance:</strong> ₹" + account.getBalance() + "</p></div><p style=\"font-size:14px; color:#555;\">If you did not authorize this transaction, please contact our support team immediately.</p><p style=\"font-size:14px; color:#555; margin-top:25px;\">Thank you for banking with us.<br><strong>Keystone Banking Team</strong></p></td></tr><tr><td style=\"background-color:#f4f6f8; text-align:center; padding:15px; font-size:12px; color:#777;\">© 2026 Keystone Banking. All rights reserved.</td></tr></table></body></html>";
		emailService.sendMail(account.getEmail(), subject, message);
		
		return AccountBalanceMapper.toBalanceDTO(account);
		
	}


//--------------------------------------------------------DEPOSIT AMOUNT--------------------------------------------------------------------
	@Transactional
	@Override
	public BalanceDTO depositAmount(Long acno, Double amount) {
		
		adv.validateAmount(amount);
		
		Account account = this.getByAccountNumber(acno);
		account.setBalance(account.getBalance()+amount);
		
		setTransaction(acno, amount, TransactionType.CREDIT);
		String subject = "Deposit Successful";
		String message = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Deposit Successful</title></head><body style=\"margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f6f8;\"><table align=\"center\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#ffffff; margin-top:30px; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);\"><tr><td style=\"background-color:#1a73e8; padding:20px; text-align:center; color:white;\"><h2 style=\"margin:0;\">Keystone Banking</h2></td></tr><tr><td style=\"padding:30px;\"><h3 style=\"color:#333;\">Deposit Successful ✅</h3><p style=\"font-size:15px; color:#555;\">Dear Customer,</p><p style=\"font-size:15px; color:#555;\">We are pleased to inform you that the following amount has been successfully credited to your account:</p><div style=\"background:#f1f5f9; padding:15px; border-radius:6px; margin:20px 0;\"><p style=\"margin:5px 0; font-size:16px;\"><strong>Deposited Amount:</strong> ₹" + amount + "</p><p style=\"margin:5px 0; font-size:16px;\"><strong>Available Balance:</strong> ₹" + account.getBalance() + "</p></div><p style=\"font-size:14px; color:#555;\">If you did not perform this transaction, please contact our support team immediately.</p><p style=\"font-size:14px; color:#555; margin-top:25px;\">Thank you for banking with us.<br><strong>Keystone Banking Team</strong></p></td></tr><tr><td style=\"background-color:#f4f6f8; text-align:center; padding:15px; font-size:12px; color:#777;\">© 2026 Keystone Banking. All rights reserved.</td></tr></table></body></html>";
		emailService.sendMail(account.getEmail(), subject, message);

		return AccountBalanceMapper.toBalanceDTO(account);
	}


	@Override
	public void setTransaction(Long acccountno, Double amount, TransactionType transactionType) {
		transactionRepository.save(new Transactions(acccountno, amount, transactionType));
	}


	


}
