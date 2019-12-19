package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Adress implements Serializable {
	
	private static final long serialVersionUID = -2129653198171527470L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
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
	
	public Adress() {};
	
	public Adress(String street, String streetNumber, String place, int postcode) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.place = place;
		this.postcode = postcode;
	}
	
	public Long getId() {
		return id;
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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Adress other = (Adress) o;
		if (!Objects.equals(this.id, other.id)) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (this.id == null) {
			return 0;
		} else {
			return this.id.hashCode();
		}
	}
	
}
