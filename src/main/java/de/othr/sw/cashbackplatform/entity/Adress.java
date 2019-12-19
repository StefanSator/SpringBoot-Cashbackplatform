package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Adress extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = -2129653198171527470L;
	
	@NotNull
	@Size(max=60)
	@Pattern(regexp="^[A-Z][a-z]+[.]{0,1}")
	private String street;
	@NotNull
	@Size(max=10)
	@Pattern(regexp="^[1-9][0-9]*[a-z]{0,1}")
	private String streetNumber;
	@NotNull
	@Size(max=60)
	@Pattern(regexp="^[A-Z][a-z]+")
	private String place;
	@NotNull
	private Integer postcode;
	
	public Adress() { super(); };
	
	public Adress(String street, String streetNumber, String place, int postcode) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.place = place;
		this.postcode = postcode;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public int getPostcode() {
		return postcode;
	}
	
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
}
