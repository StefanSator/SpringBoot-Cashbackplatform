package de.othr.sw.cashbackplatform.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
public abstract class Customer implements UserDetails {
	
	private static final long serialVersionUID = -3859631257104556158L;
	
	@Id
	@NotNull
	@Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
	private String email;
	/*  Be between 8 and 40 characters long
		Contain at least one digit.
		Contain at least one lower case character.
		Contain at least one upper case character.
		Contain at least on special character from [ @ # $ % ! . ] */
	@NotNull
	//@Pattern(regexp="((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})")
	private String password;
	@NotNull
	@Pattern(regexp="\\d{1,15}")
	private String telephone;
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@Valid
	private Adress adress;
	
	public Customer() {}
	
	public Customer(String email, String password, String telephone, Adress adress) {
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.adress = adress;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTelephoneNumber() {
		return telephone;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		telephone = telephoneNumber;
	}
	
	public Adress getAdress() {
		return this.adress;
	}
	
	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (this instanceof Shop) {
			authorities.add(new Authority("ROLE_SHOP"));
		}
		if (this instanceof PrivateCustomer) {
			authorities.add(new Authority("ROLE_CUSTOMER"));
		}
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
