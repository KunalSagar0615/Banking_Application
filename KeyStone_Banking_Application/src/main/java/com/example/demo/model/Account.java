package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "AccountType" , discriminatorType = DiscriminatorType.STRING)
@Table(name = "KeyStone_Banking_Application")
public class Account {

	@Id
	private Long acno;
	
	@NotBlank
	private String name;
	
	@Email(message = "Invalid email formate")
	@NotBlank(message = "Email is required")
	@Column(unique = true)
	private String email;
	
	@NotNull
	@Digits(integer=12 ,fraction=0,message="Adhar number must exactly 12 digits")
	@Column(unique = true)
	private Long adharNo;
	
	@NotBlank
	@Size(min=10,max=10)
	private String mob;
	
	private String address;
	
	private LocalDate createdDate;
	
	@PrePersist //Run this method just before the entity is saved
	public void onCreate() {
		this.createdDate=LocalDate.now();
	}
	
	@NotNull(message = "You should have to add amount")
	private Double balance;
	
}
