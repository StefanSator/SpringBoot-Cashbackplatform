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
import de.othr.sw.cashbackplatform.service.CashbackServiceIF;

@Controller
@RequestMapping("/cashbacks")
public class CashbackController {
	@Autowired
	CashbackServiceIF cashbackService;
	
	@RequestMapping("/display")
	public String cashback(@AuthenticationPrincipal Customer principal, Model model) {
		if (principal instanceof PrivateCustomer) {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) principal);
			model.addAttribute("cashbacks", cashbacks);
		} else {
			
		}
		return "cashbackoverview";
	}
}
