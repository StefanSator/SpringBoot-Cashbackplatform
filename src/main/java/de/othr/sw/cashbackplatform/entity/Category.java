package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Category implements Serializable {
	
	private static final long serialVersionUID = -3931867895683087076L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryId;
	@NotNull
	@Size(max = 50)
	@Pattern(regexp="\\w+")
	public String category;
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Shop owner;
	
	public Category() {}
	
	public Category(String category, Shop owner) {
		this.category = category;
		this.owner = owner;
	}
	
	public long getCategoryId() {
		return this.categoryId;
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
		if (!Objects.equals(this.categoryId, other.categoryId)) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (this.categoryId == null) {
			return 0;
		} else {
			return this.categoryId.hashCode();
		}
	}
}
