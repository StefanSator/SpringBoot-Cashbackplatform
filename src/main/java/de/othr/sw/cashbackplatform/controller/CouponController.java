package de.othr.sw.cashbackplatform.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.DailyRecommendation;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.CouponInvalidException;
import de.othr.sw.cashbackplatform.service.CouponService;
import de.othr.sw.cashbackplatform.service.CustomerService;

@Controller
@RequestMapping("/coupons")
public class CouponController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CouponService couponService;
	
	@RequestMapping("/display")
	public String displayCoupons(Model model) {
		model.addAttribute("isActive", 2);
		DailyRecommendation recommended = couponService.getDailyRecommendation(new Date());
		model.addAttribute("dailyrecommendation", recommended);
		List<Coupon> current = couponService.getAllCurrentCoupons(new Date());
		model.addAttribute("currentcoupons", current);
		List<Coupon> upcoming = couponService.getAllUpcomingCoupons(new Date());
		model.addAttribute("upcomingcoupons", upcoming);
		return "coupon";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manageCoupons(Model model, @AuthenticationPrincipal Customer customer) {
		model.addAttribute("isActive", 2);
		if (customer instanceof Shop && customer.getUsername() != null) {
			List<Category> categories = ((Shop) customer).getCategories();
			model.addAttribute("shopcategories", categories);
		}
		return "couponmanagement";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public String createCoupon(@ModelAttribute("couponname") String couponname,
								  @ModelAttribute("beginDate") String beginDate,
								  @ModelAttribute("endDate") String endDate,
								  @ModelAttribute("category") Long categoryId,
								  @ModelAttribute("multiplicator") Integer multiplicator, 
								  Model model,
								  @AuthenticationPrincipal Customer customer) {
		model.addAttribute("isActive", 2);
		Shop categoryOwner = (Shop) customer;
		model.addAttribute("shopcategories", categoryOwner.getCategories());
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date beginDateObject = formatter.parse(beginDate);
			Date endDateObject = formatter.parse(endDate);
			Category couponCategory = customerService.getShopCategory(categoryId);
			Coupon coupon = new Coupon(couponname, beginDateObject, endDateObject, multiplicator, couponCategory, categoryOwner);
			Coupon registeredCoupon = couponService.registerCoupon(coupon);
			model.addAttribute("registration", "Wir haben Ihren eCoupon erfolgreich registriert.");
		} catch (Exception ex) {
			if (ex instanceof CouponInvalidException) {
				model.addAttribute("error", ex.getMessage());
			} else {
				model.addAttribute("error", "Eingegebene Formulardaten sind ungültig. Bitte überprüfen Sie ob die Daten ein gültiges Format besitzen oder ob Sie alle Daten ausgefüllt haben.");
			}
		}
		return "couponmanagement";
	}
	
}
