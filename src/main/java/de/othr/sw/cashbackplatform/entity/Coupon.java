package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Coupon extends GeneratedIdEntity implements Serializable {
	
	private static final long serialVersionUID = -915398441144397322L;
	
	@NotNull
	@Size(max = 50)
	@Pattern(regexp="\\w+")
	private String couponName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@NotNull
	private Double cashbackPointsMultiplicator;
	@NotNull
	@ManyToOne
	private Category couponCategory;
	@NotNull
	@ManyToOne
	private Shop owner;
	
	public Coupon() { super(); }
	
	public Coupon(String couponName, Date beginDate, Date endDate, double pointsMultiplicator, Category couponCategory, Shop owner) {
		super();
		this.couponName = couponName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.cashbackPointsMultiplicator = pointsMultiplicator;
		this.couponCategory = couponCategory;
		this.owner = owner;
	}
	
	public String getCouponName() {
		return this.couponName;
	}
	
	public void setCouponName(String name) {
		this.couponName = name;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	
	public void setBeginDate(Date date) {
		this.beginDate = date;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	
	public double getCashbackPointsMultiplicator() {
		return this.cashbackPointsMultiplicator;
	}
	
	public void setCashbackPointsMultiplicator(double multiplicator) {
		this.cashbackPointsMultiplicator = multiplicator;
	}
	
	public Category getCouponCategory() {
		return this.couponCategory;
	}
	
	public void setCouponCategory(Category category) {
		this.couponCategory = category;
	}
	
	public Shop getOwner() {
		return this.owner;
	}
	
	public void setOwner(Shop owner) {
		this.owner = owner;
	}
	
}
