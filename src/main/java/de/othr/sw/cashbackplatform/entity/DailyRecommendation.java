package de.othr.sw.cashbackplatform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Access(AccessType.FIELD)
public class DailyRecommendation extends DateIdEntity implements Serializable {

	private static final long serialVersionUID = -4438482845857872409L;

	@NotNull
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Coupon recommendedCoupon;
	
	public DailyRecommendation() { super(); }
	
	public DailyRecommendation(Coupon coupon, Date date) {
		super(date);
		this.recommendedCoupon = coupon;
	}
	
	public Coupon getRecommendedCoupon() {
		return this.recommendedCoupon;
	}
	
	public void setRecommendedCoupon(Coupon coupon) {
		this.recommendedCoupon = coupon;
	}
	
	public Date getRecommendationDate() {
		return super.getId();
	}
	
	public void setRecommendationDate(Date date) {
		super.setId(date);
	}
}
