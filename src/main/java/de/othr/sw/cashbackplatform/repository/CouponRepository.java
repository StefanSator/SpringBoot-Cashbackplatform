package de.othr.sw.cashbackplatform.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Coupon;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long> {
	List<Coupon> findByEndDateBetweenAndCouponCategoryIs(Date start, Date ende, Category category);
	List<Coupon> findByEndDateGreaterThanEqualAndBeginDateLessThanEqual(Date date1, Date date2);
	Page<Coupon> findByEndDateGreaterThanEqualAndBeginDateLessThanEqual(Date date1, Date date2, Pageable pageable);
	List<Coupon> findByBeginDateAfter(Date date);
	Long countByEndDateGreaterThanEqualAndBeginDateLessThanEqual(Date date1, Date date2);
}
