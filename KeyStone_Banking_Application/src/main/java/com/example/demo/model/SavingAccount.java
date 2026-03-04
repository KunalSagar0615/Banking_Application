package com.example.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

//@Data
@Entity
@DiscriminatorValue("SAVING")
public class SavingAccount extends Account {


}
