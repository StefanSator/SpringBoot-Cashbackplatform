package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Coupon implements Serializable {
	
	private static final long serialVersionUID = -915398441144397322L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long couponId;
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
	
	public Coupon() {}
	
	public Coupon(String couponName, Date beginDate, Date endDate, double pointsMultiplicator, Category couponCategory, Shop owner) {
		this.couponName = couponName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.cashbackPointsMultiplicator = pointsMultiplicator;
		this.couponCategory = couponCategory;
		this.owner = owner;
	}
	
	public long getCouponId() {
		return this.couponId;
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
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Coupon other = (Coupon) o;
		if (!Objects.equals(this.couponId, other.couponId)) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (this.couponId == null) {
			return 0;
		} else {
			return this.couponId.hashCode();
		}
	}
	
}
