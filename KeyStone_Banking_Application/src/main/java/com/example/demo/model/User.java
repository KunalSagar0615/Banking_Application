package com.example.demo.model;
import com.example.demo.enumeration.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User{

	@Id
	private Integer empId;
	private String email;
	private String name;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
