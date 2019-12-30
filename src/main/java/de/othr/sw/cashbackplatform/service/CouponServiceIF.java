package de.othr.sw.cashbackplatform.service;

import java.util.Date;
import java.util.List;

import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.exceptions.CouponInvalidException;

public interface CouponServiceIF {
	public Coupon registerCoupon(Coupon coupon) throws CouponInvalidException;
	public List<Coupon> getAllCouponsAfterDate(Date date);
}
