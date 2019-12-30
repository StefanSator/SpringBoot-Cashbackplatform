package de.othr.sw.cashbackplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupons")
public class CouponController {
	
	@RequestMapping("/display")
	public String displayCoupons() {
		System.out.println("I am here.");
		return "coupon";
	}
}
