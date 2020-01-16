package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

@Entity
@Access(AccessType.FIELD)
public class Cashbackposition extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = -8197800294348266681L;
	
	@NotNull
	private Integer singleCashbackPoints;
	@ManyToOne
	private Category cashbackCategory;
	
	public Cashbackposition() { super(); }
	
	public Cashbackposition(int cashbackpoints, Category category) {
		super();
		this.singleCashbackPoints = cashbackpoints;
		this.cashbackCategory = category;
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
	
}
