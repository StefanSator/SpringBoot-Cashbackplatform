package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class PrivateCustomer extends Customer implements Serializable {
	
	private static final long serialVersionUID = -3879548003144583603L;
	
	@NotNull
	@Pattern(regexp="\\w+")
	private String surname;
	@NotNull
	@Pattern(regexp="\\w+")
	private String name;
	@NotNull
	private String accountIdentification;
	@NotNull
	private Long accountBalance;
	
	public PrivateCustomer() {
		super();
	}

	public PrivateCustomer(String email, String password, String telephone, Adress adress, String surname, String name, long accountBalance) {
		super(email, password, telephone, adress);
		this.accountIdentification = "ID" + surname.toUpperCase() + name.toUpperCase() + super.getId();
		this.surname = surname;
		this.name = name;
		this.accountBalance = accountBalance;
	}
	
	public String getAccountIdentification() {
		return accountIdentification;
	}
	
	public void updateAccountIdentification() {
		this.accountIdentification = "ID" + surname.toUpperCase() + name.toUpperCase() + super.getId();
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
		this.updateAccountIdentification();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.updateAccountIdentification();
	}
	
	public Long getAccountBalance() {
		return this.accountBalance;
	}
	
	public void setAccountBalance(long balance) {
		this.accountBalance = balance;
	}
	
	public void addToAccountBalance(long points) {
		this.accountBalance += points;
	}
	
	public void subtractFromAccountBalance(long points) {
		this.accountBalance -= points;
	}

}
