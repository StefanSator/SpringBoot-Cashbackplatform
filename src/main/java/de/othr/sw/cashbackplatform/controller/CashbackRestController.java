package de.othr.sw.cashbackplatform.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.othr.sw.cashbackplatform.dto.BalanceDTO;
import de.othr.sw.cashbackplatform.dto.CashbackDTO;
import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.service.CashbackServiceIF;
import de.othr.sw.cashbackplatform.service.CustomerServiceIF;

@RestController
public class CashbackRestController {
	@Autowired
	private CashbackServiceIF cashbackService;
	@Autowired
	private CustomerServiceIF customerService;
	
	
	@RequestMapping(value="/restapi/cashback/{accountnr}", method = RequestMethod.GET)
	public BalanceDTO getCashbackBalance(@PathVariable("accountnr") String accountidentification) {
		try {
			PrivateCustomer customer = customerService.getPrivateCustomerWithAccountIdentification(accountidentification);
			return new BalanceDTO(new Date(), customer.getAccountIdentification(), customer.getSurname(), customer.getName(), customer.getAccountBalance());
		} catch (Exception ex) {
			// TODO: Ask Prof how to send error to rest client
			ex.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/restapi/cashback/debit", method = RequestMethod.POST)
	public CashbackDTO debitCashbackAccount(@RequestBody PurchaseDTO purchase) {
		//return cashbackService.debitCashbackAccount(purchase);
		return null;
	}
	
	@RequestMapping(value="/restapi/cashback/accredit", method = RequestMethod.POST)
	public CashbackDTO accreditCashbackAccount(@RequestBody PurchaseDTO purchase) {
		//return cashbackService.accreditCashbackAccount(purchase);
		return null;
	}
}
