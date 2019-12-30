package de.othr.sw.cashbackplatform.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
	List<Coupon> findByEndDateBetweenAndCouponCategoryIs(Date start, Date ende, Category category);
	List<Coupon> findByEndDateGreaterThanEqual(Date date);
}
