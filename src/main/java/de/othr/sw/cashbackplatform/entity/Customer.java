package de.othr.sw.cashbackplatform.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "customertype")
@Access(AccessType.FIELD)
public abstract class Customer extends GeneratedIdEntity implements UserDetails {
	
	private static final long serialVersionUID = -3859631257104556158L;
	
	@NotNull
	@Column(unique = true)
	@Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
	private String email;
	@NotNull
	private String password;
	@NotNull
	@Pattern(regexp="\\d{1,15}")
	private String telephone;
	@NotNull
	@OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST)
	@Valid
	private Adress adress;
	
	public Customer() { super(); }
	
	public Customer(String email, String password, String telephone, Adress adress) {
		super();
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.adress = adress;
	}
	
	public String getEmail() {
		return this.email;
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
