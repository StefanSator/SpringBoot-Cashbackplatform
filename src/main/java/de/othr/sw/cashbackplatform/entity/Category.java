package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Category extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = -3931867895683087076L;
	
	@NotNull
	@Size(max = 50)
	@Pattern(regexp="\\w+")
	public String category;
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Shop owner;
	
	public Category() { super(); }
	
	public Category(String category, Shop owner) {
		super();
		this.category = category;
		this.owner = owner;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String name) {
		this.category = name;
	}
	
	public Shop getOwner() {
		return this.owner;
	}
	
	public void setOwner(Shop owner) {
		this.owner = owner;
	}
	
}
