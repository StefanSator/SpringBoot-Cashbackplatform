package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class PrivateCustomer extends Customer implements Serializable {
	
	private static final long serialVersionUID = -3879548003144583603L;
	
	@NotNull
	@Size(max = 30)
	@Pattern(regexp="\\w+")
	private String surname;
	@NotNull
	@Size(max = 30)
	@Pattern(regexp="\\w+")
	private String name;
	@NotNull
	private String accountIdentification;
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int accountNumber;
	
	public PrivateCustomer() {
		super();
	}

	public PrivateCustomer(String email, String password, String telephone, Adress adress, String surname, String name) {
		super(email, password, telephone, adress);
		this.accountIdentification = "ID" + surname + name + this.accountNumber;
		this.surname = surname;
		this.name = name;
	}
	
	public String getAccountIdentification() {
		return accountIdentification;
	}
	
	public void updateAccountIdentification() {
		this.accountIdentification = "ID" + surname + name + this.accountNumber;
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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final PrivateCustomer other = (PrivateCustomer) o;
		if (!Objects.equals(this.getEmail(), other.getEmail())) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (this.getEmail() == null) {
			return 0;
		} else {
			return this.getEmail().hashCode();
		}
	}

}
