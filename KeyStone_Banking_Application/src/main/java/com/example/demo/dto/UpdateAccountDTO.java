package com.example.demo.dto;
import com.example.demo.model.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO {

	private String name;
	private String email;
	private String mob;
	private String address;

	public static UpdateAccountDTO toAccountDTO(Account account) {
		UpdateAccountDTO dto=new UpdateAccountDTO();
		
		dto.setName(account.getName());
		dto.setEmail(account.getEmail());
		dto.setMob(account.getMob());
		dto.setAddress(account.getAddress());
		
		return dto;
	}
	
}
