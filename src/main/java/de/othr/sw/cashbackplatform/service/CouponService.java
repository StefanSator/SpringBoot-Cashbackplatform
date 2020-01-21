package de.othr.sw.cashbackplatform.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.entity.DailyRecommendation;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.CouponInvalidException;
import de.othr.sw.cashbackplatform.repository.CouponRepository;
import de.othr.sw.cashbackplatform.repository.DailyRecommendationRepository;

@Service
public class CouponService implements CouponServiceIF {
	@Autowired
	private CouponRepository couponRepo;
	@Autowired
	private DailyRecommendationRepository dailyRecommendationRepo;

	@Override
	public Coupon registerCoupon(Coupon coupon) throws CouponInvalidException {
		// Check if Coupon is valid
		// Check if date inputs are valid
		if (!(coupon.getEndDate().compareTo(coupon.getBeginDate()) >= 0)) {
			throw new CouponInvalidException("Ungültige Eingabe. Das Enddatum darf nicht vor dem Beginndatum des Coupons liegen.");
		}
		if (coupon.getBeginDate().compareTo(new Date()) < 0) {
			throw new CouponInvalidException("Ungültige Eingabe. Das Beginndatum darf nicht in der Vergangenheit liegen.");
		}
		// Check if there exists already a coupon for the specified category during the specified date interval
		List<Coupon> existingCoupons = couponRepo.findByBeginDateLessThanEqualAndEndDateGreaterThanEqualAndCouponCategoryIs(coupon.getEndDate(), coupon.getBeginDate(), coupon.getCouponCategory());
		if (existingCoupons.size() != 0) {
			throw new CouponInvalidException("Ungültiger Coupon. Es existiert bereits ein Coupon für diese Kategorie im angegebenen Zeitintervall."
					+ " Achten Sie bitte darauf, dass das Beginndatum nach dem Enddatum des aktuell noch laufenden Coupons liegt.");
		}
		Coupon registeredCoupon = couponRepo.save(coupon);
		return registeredCoupon;
	}
	
	/* 
	 * Diese Funktion sollte normalerweise jeden Tag um 0 Uhr ausgeführt werden.
	 * Zu Veranschaulichungszwecken habe ich es aber so eingestellt, das das Programm
	 * nun jede Minute einen neuen Coupon als tägliche Empfehlung speichert.
	 */
	@Override
	@Scheduled(cron = "0 0/1 * * * *", zone="Europe/Berlin")
	public void recommendRandomDailyCoupon() {
		Date currentDate = new Date();
		Long countCurrentCoupons = couponRepo.countByEndDateGreaterThanEqualAndBeginDateLessThanEqual(currentDate, currentDate);
		int index = (int) (Math.random() * countCurrentCoupons);
		Page<Coupon> couponPage = couponRepo.findByEndDateGreaterThanEqualAndBeginDateLessThanEqual(currentDate, currentDate, PageRequest.of(index, 1));
		Coupon coupon = null;
		if (couponPage.hasContent()) {
			coupon = couponPage.getContent().get(0);
			DailyRecommendation dailyRecommendation = new DailyRecommendation(coupon, currentDate);
			dailyRecommendation = dailyRecommendationRepo.save(dailyRecommendation);
		}
	}
	
	@Override
	public DailyRecommendation getDailyRecommendation(Date currentdate) {
		DailyRecommendation recommendation = dailyRecommendationRepo.findById(currentdate).orElse(null);
		return recommendation;
	}
	
	@Override
	public List<Coupon> getAllCurrentCoupons(Date date) {
		List<Coupon> coupons = couponRepo.findByEndDateGreaterThanEqualAndBeginDateLessThanEqual(date, date);
		return coupons;
	}
	
	@Override
	public List<Coupon> getAllCurrentCouponsOfShop(Date date, Shop shop) {
		List<Coupon> coupons = couponRepo.findByEndDateGreaterThanEqualAndBeginDateLessThanEqualAndOwner(date, date, shop);
		return coupons;
	}
	
	@Override
	public List<Coupon> getAllUpcomingCouponsOfShop(Date date, Shop shop) {
		List<Coupon> coupons = couponRepo.findByBeginDateAfterAndOwner(date, shop);
		return coupons;
	}
	
	@Override
	public List<Coupon> getAllUpcomingCoupons(Date date) {
		List<Coupon> coupons = couponRepo.findByBeginDateAfter(date);
		return coupons;
	}

}
