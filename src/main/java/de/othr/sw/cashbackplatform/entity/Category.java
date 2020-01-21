package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Category extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = -3931867895683087076L;
	
	@NotNull
	@Size(max = 200)
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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Category other = (Category) o;
		if (Objects.equals(this.category, other.category) && Objects.equals(this.owner, other.owner)) return true;
		return false;
	}
	
}
