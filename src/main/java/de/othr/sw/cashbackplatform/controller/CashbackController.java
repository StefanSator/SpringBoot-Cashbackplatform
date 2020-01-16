package de.othr.sw.cashbackplatform.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String displayCashbacks(@AuthenticationPrincipal Customer principal, Model model) {
		model.addAttribute("isActive", 3);
		Date to = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(to);
		calendar.add(Calendar.DATE, -10);
		Date from = calendar.getTime();
		SimpleDateFormat sdfr = new SimpleDateFormat("dd MMM yyyy");
		model.addAttribute("from", sdfr.format(from));
		model.addAttribute("to", sdfr.format(new Date()));
		if (principal instanceof PrivateCustomer) {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) principal, from, new Date());
			model.addAttribute("cashbacks", cashbacks);
		} else {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) principal, from, new Date());
			model.addAttribute("cashbacks", cashbacks);
		}
		return "cashbackoverview";
	}
	
	@RequestMapping("/display/filter")
	public String filterCashbacks(@RequestParam(name = "fromdate") String from,
								  @RequestParam(name = "todate") String to,
								  @AuthenticationPrincipal Customer principal, 
								  Model model) {
		model.addAttribute("isActive", 3);
		SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfr2 = new SimpleDateFormat("dd MMM yyyy");
		Date beginDate;
		Date endDate;
		try {
			beginDate = sdfr.parse(from);
			endDate = sdfr.parse(to);
			model.addAttribute("from", sdfr2.format(beginDate));
			model.addAttribute("to", sdfr2.format(endDate));
			if (principal instanceof PrivateCustomer) {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) principal, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
			} else {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) principal, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
			}
		} catch (Exception e) {
			model.addAttribute("error", "Leider ist bei der Filterung etwas fehlgeschlagen. Bitte überprüfen Sie das das Datum ein korrektes Format besitzt.");
			beginDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -10);
			endDate = calendar.getTime();
			model.addAttribute("from", sdfr2.format(beginDate));
			model.addAttribute("to", sdfr2.format(endDate));
			if (principal instanceof PrivateCustomer) {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) principal, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
			} else {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) principal, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
			}
		}
		return "cashbackoverview";
	}
}
