package de.othr.sw.cashbackplatform.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.exceptions.CouponInvalidException;
import de.othr.sw.cashbackplatform.repository.CouponRepository;

@Service
public class CouponService implements CouponServiceIF {
	@Autowired
	private CouponRepository couponRepo;

	@Override
	public Coupon registerCoupon(Coupon coupon) throws CouponInvalidException {
		// Check if Coupon is valid
		// Check if date inputs are valid
		if (!(coupon.getEndDate().compareTo(coupon.getBeginDate()) >= 0)) {
			throw new CouponInvalidException("Ungültige Eingabe. Das Enddatum darf nicht vor dem Beginndatum des Coupons liegen.");
		}
		if (!(coupon.getBeginDate().compareTo(new Date()) >= 0)) {
			throw new CouponInvalidException("Ungültige Eingabe. Das Beginndatum darf nicht in der Vergangenheit liegen.");
		}
		// Check if there exists already a coupon for the specified category during the specified date interval
		List<Coupon> existingCoupons = couponRepo.findByEndDateBetweenAndCouponCategoryIs(coupon.getBeginDate(), coupon.getEndDate(), coupon.getCouponCategory());
		if (existingCoupons.size() != 0) {
			throw new CouponInvalidException("Ungültiger Coupon. Es existiert bereits ein Coupon für diese Kategorie im angegebenen Zeitintervall."
					+ "Achten Sie bitte darauf, dass das Beginndatum nach dem Enddatum des aktuell noch laufenden Coupons liegt.");
		}
		Coupon registeredCoupon = couponRepo.save(coupon);
		return registeredCoupon;
	}
	
	@Override
	public List<Coupon> getAllCouponsAfterDate(Date date) {
		List<Coupon> coupons = couponRepo.findByEndDateGreaterThanEqual(date);
		return coupons;
	}

}
