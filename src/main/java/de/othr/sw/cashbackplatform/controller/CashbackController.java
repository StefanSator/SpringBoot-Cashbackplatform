package de.othr.sw.cashbackplatform.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.service.CashbackServiceIF;
import de.othr.sw.cashbackplatform.service.CustomerServiceIF;
import de.othr.sw.cashbackplatform.service.PaymentServiceIF;

@Controller
@RequestMapping("/cashbacks")
public class CashbackController {
	@Autowired
	CashbackServiceIF cashbackService;
	@Autowired
	CustomerServiceIF customerService;
	@Autowired
	PaymentServiceIF paymentService; // dummy service
	
	@RequestMapping("/display")
	public String displayCashbacks(@AuthenticationPrincipal Customer principal, Model model) {
		model.addAttribute("isActive", 3);
		Customer customer = customerService.getCustomerByEmail(principal.getEmail());
		Date to = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(to);
		calendar.add(Calendar.DATE, -10);
		Date from = calendar.getTime();
		SimpleDateFormat sdfr = new SimpleDateFormat("dd MMM yyyy");
		model.addAttribute("from", sdfr.format(from));
		model.addAttribute("to", sdfr.format(new Date()));
		if (customer instanceof PrivateCustomer) {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) customer, from, new Date());
			model.addAttribute("cashbacks", cashbacks);
			model.addAttribute("cashbackpoints", ((PrivateCustomer) customer).getAccountBalance());
		} else {
			List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) customer, from, new Date());
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
		Customer customer = customerService.getCustomerByEmail(principal.getEmail());
		SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfr2 = new SimpleDateFormat("dd MMM yyyy");
		Date beginDate;
		Date endDate;
		try {
			beginDate = sdfr.parse(from);
			endDate = sdfr.parse(to);
			model.addAttribute("from", sdfr2.format(beginDate));
			model.addAttribute("to", sdfr2.format(endDate));
			if (customer instanceof PrivateCustomer) {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) customer, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
				model.addAttribute("cashbackpoints", ((PrivateCustomer) customer).getAccountBalance());
			} else {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) customer, beginDate, endDate);
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
			if (customer instanceof PrivateCustomer) {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfPrivateCustomer((PrivateCustomer) customer, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
				model.addAttribute("cashbackpoints", ((PrivateCustomer) customer).getAccountBalance());
			} else {
				List<Cashback> cashbacks = cashbackService.getAllCashbacksOfShop((Shop) customer, beginDate, endDate);
				model.addAttribute("cashbacks", cashbacks);
			}
		}
		return "cashbackoverview";
	}
	
	@RequestMapping("/chargepoints")
	public String chargeCashbackpoints(@ModelAttribute("iban") String iban,
									   @AuthenticationPrincipal Customer principal, 
									   Model model) {
		model.addAttribute("isActive", 3);
		try {
			Customer customer = customerService.getCustomerByEmail(principal.getEmail());
			long cashbackbalance = ((PrivateCustomer) customer).getAccountBalance();
			if (iban.isBlank()) {
				model.addAttribute("error", "Bitte füllen Sie das IBAN Formularfeld aus.");
			} else if (cashbackbalance != 0) {
				double amount = cashbackService.grantMoneyForCashbackPointsOfCustomer((PrivateCustomer) customer, iban);
				model.addAttribute("success", "Wir haben Ihrem Konto erfolgreich " + amount + " € gutgeschrieben.");
			} else {
				model.addAttribute("error", "Sie besitzen leider keine Punkte auf Ihrem Konto.");
			}
		} catch (Exception e) {
			model.addAttribute("error", "Leider ist ein Fehler aufgetreten und wir konnten Ihnen das Guthaben nicht Ihrem Konto überweisen.");
			e.printStackTrace();
		}
		return "chargepoints";
	}
}
