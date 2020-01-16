package de.othr.sw.cashbackplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.service.CashbackServiceIF;

@Controller
@RequestMapping("/cashbacks")
public class CashbackController {
	@Autowired
	CashbackServiceIF cashbackService;
	
	@RequestMapping("/display")
	public String cashback(@AuthenticationPrincipal Customer principal, Model model) {
		model.addAttribute("isActive", 3);
		if (principal instanceof PrivateCustomer) {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) principal);
			model.addAttribute("cashbacks", cashbacks);
		} else {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) principal);
			model.addAttribute("cashbacks", cashbacks);
		}
		return "cashbackoverview";
	}
}
