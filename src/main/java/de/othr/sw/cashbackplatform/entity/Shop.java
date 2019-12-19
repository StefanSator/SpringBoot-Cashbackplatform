package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Shop extends Customer implements Serializable {

	private static final long serialVersionUID = 957657176908163338L;
	
	@NotNull
	@Size(max = 60)
	@Pattern(regexp="\\w+")
	private String shopname;
	
	public Shop() {
		super();
	}

	public Shop(String email, String password, String telephone, Adress adress, String shopname) {
		super(email, password, telephone, adress);
		this.shopname = shopname;
	}
	
	public String getShopname() {
		return shopname;
	}
	
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Shop other = (Shop) o;
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
