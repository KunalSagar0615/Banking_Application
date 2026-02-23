package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("SAVING")
public class SavingAccount extends Account {

	@JsonIgnore
	private Double minBalance=500d;
	@JsonIgnore
	private Double withdrawLimit=100000d;
	
	
}
