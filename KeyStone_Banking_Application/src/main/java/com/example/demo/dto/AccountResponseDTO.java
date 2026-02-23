package com.example.demo.dto;

import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.SavingAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

	private String acno;
	private String name;
	private String address;
	private String email;
	private String mobileNo;
	private String accountType;
	
	public static AccountResponseDTO toAccountResponseDTO(Account account) {
		
		AccountResponseDTO dto=new AccountResponseDTO();
		
		//set account number like ***1234
		String accNo = account.getAcno().toString();
		dto.setAcno("********" + accNo.substring(accNo.length() - 4));
  
	
        //set account number
        dto.setName(account.getName());
        
        //set address
        dto.setAddress(account.getAddress());
        
        //set email
        dto.setEmail(account.getEmail());
        
        //set mobile number
        dto.setMobileNo(account.getMob());
        
        //set account type
        if(account instanceof SavingAccount)
        	dto.setAccountType("SAVING");
        else if(account instanceof CurrentAccount)
        	dto.setAccountType("CURRENT");     
		
		
		return dto;
		
	}
	
}
