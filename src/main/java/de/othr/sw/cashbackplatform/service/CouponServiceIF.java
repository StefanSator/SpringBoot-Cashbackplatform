package de.othr.sw.cashbackplatform.service;

import java.util.Date;
import java.util.List;

import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.entity.DailyRecommendation;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.CouponInvalidException;

public interface CouponServiceIF {
	public Coupon registerCoupon(Coupon coupon) throws CouponInvalidException;
	public List<Coupon> getAllCurrentCoupons(Date date);
	public List<Coupon> getAllCurrentCouponsOfShop(Date date, Shop shop);
	public List<Coupon> getAllUpcomingCouponsOfShop(Date date, Shop shop);
	public List<Coupon> getAllUpcomingCoupons(Date date);
	public void recommendRandomDailyCoupon();
	public DailyRecommendation getDailyRecommendation(Date currentdate);
}
