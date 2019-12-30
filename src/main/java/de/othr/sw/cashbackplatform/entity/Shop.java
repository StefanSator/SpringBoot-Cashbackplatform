package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Shop extends Customer implements Serializable {

	private static final long serialVersionUID = 957657176908163338L;
	
	@NotNull
	@Size(max = 60)
	//@Pattern(regexp="\\w+")
	private String shopname;
	private Integer defaultCashbackPerSale;
	private String shopinfo;
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Category> categories;
	
	public Shop() {
		super();
	}

	public Shop(String email, String password, String telephone, Adress adress, String shopname, String shopinfo, Integer defaultCashbackPerSale) {
		super(email, password, telephone, adress);
		this.shopname = shopname;
		this.shopinfo = shopinfo;
		this.defaultCashbackPerSale = defaultCashbackPerSale;
	}
	
	public String getShopname() {
		return shopname;
	}
	
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	
	public String getShopinfo() {
		return this.shopinfo;
	}
	
	public void setShopinfo(String shopinfo) {
		this.shopinfo = shopinfo;
	}
	
	public Integer getDefaultCashbackPointsPerSale() {
		return this.defaultCashbackPerSale;
	}
	
	public void setDefaultCashbackPointsPerSale(Integer defaultCashbackPerSale) {
		this.defaultCashbackPerSale = defaultCashbackPerSale;
	}
	
	public List<Category> getCategories() {
		return this.categories;
	}
	
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public Category getCategory(int index) {
		return this.categories.get(index);
	}
	
	public void setCategory(int index, Category category) {
		this.categories.set(index, category);
	}
	
	public Category removeCategory(int index) {
		return this.categories.remove(index);
	}
	
	public boolean appendCategory(Category category) {
		return this.categories.add(category);
	}

}
