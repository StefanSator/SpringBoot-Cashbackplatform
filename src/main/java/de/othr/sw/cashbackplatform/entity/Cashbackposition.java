package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class Cashbackposition implements Serializable {
	
	private static final long serialVersionUID = -8197800294348266681L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private Integer singleCashbackPoints;
	@NotNull
	@ManyToOne
	private Category cashbackCategory;
	
	public Cashbackposition() {}
	
	public Cashbackposition(int cashbackpoints, Category category) {
		this.singleCashbackPoints = cashbackpoints;
		this.cashbackCategory = category;
	}
	
	public long getId() {
		return id;
	}
	
	public int getSingleCashbackPoints() {
		return this.singleCashbackPoints;
	}
	
	public void setSingleCashbackPoints(int cashbackpoints) {
		this.singleCashbackPoints = cashbackpoints;
	}
	
	public Category getCashbackCategory() {
		return this.cashbackCategory;
	}
	
	public void setCashbackCategory(Category category) {
		this.cashbackCategory = category;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Cashbackposition other = (Cashbackposition) o;
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
