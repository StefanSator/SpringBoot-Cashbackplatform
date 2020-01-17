package de.othr.sw.cashbackplatform.controller;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.othr.sw.cashbackplatform.dto.BalanceDTO;
import de.othr.sw.cashbackplatform.dto.CashbackDTO;
import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Authority;
import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.Cashbackposition;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.exceptions.CashbackServiceException;
import de.othr.sw.cashbackplatform.service.CashbackServiceIF;
import de.othr.sw.cashbackplatform.service.CustomerServiceIF;

@RestController
@RequestMapping("/restapi")
public class CashbackRestController {
	@Autowired
	private CashbackServiceIF cashbackService;
	@Autowired
	private CustomerServiceIF customerService;
	
	@RequestMapping(value="/cashback/{accountnr}", method = RequestMethod.GET)
	public BalanceDTO getCashbackBalance(@PathVariable("accountnr") String accountidentification) throws CashbackServiceException {
		try {
			PrivateCustomer customer = customerService.getPrivateCustomerWithAccountIdentification(accountidentification);
			BalanceDTO balance = new BalanceDTO(new Date(), customer.getAccountIdentification(), customer.getSurname(), customer.getName(), customer.getAccountBalance());
			return balance;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof CashbackServiceException) {
				throw (CashbackServiceException) ex;
			}
			throw new CashbackServiceException("Leider konnten wir Ihre Anfrage nicht korrekt bearbeiten. Bitte überprüfen Sie "
					+ "ob Sie unsere REST-API richtig verwenden oder versuchen Sie es zu einem späteren Zeitpunkt nocheinmal.");
		}
	}
	
	/* 
	@RequestMapping(value="/cashback/debit", method = RequestMethod.POST)
	public CashbackDTO debitCashbackAccount(@RequestBody PurchaseDTO purchase) {
		//return cashbackService.debitCashbackAccount(purchase);
		return null;
	} */
	
	@RequestMapping(value="/cashback/accredit", method = RequestMethod.POST)
	@Transactional
	public CashbackDTO accreditCashbackAccount(@RequestBody PurchaseDTO purchase) throws CashbackServiceException  {
		try {
			Cashback cashback = cashbackService.accreditCashbackAccount(purchase);
			int grantedPoints = 0;
			for (Cashbackposition cashbackposition : cashback.getCashbackpositions()) {
				grantedPoints += cashbackposition.getSingleCashbackPoints();
			}
			return new CashbackDTO(cashback.getReceiver().getAccountIdentification(), grantedPoints, cashback.isTransferPositive());
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof CashbackServiceException) {
				throw (CashbackServiceException) ex;
			}
			throw new CashbackServiceException("Leider konnten wir Ihre Anfrage nicht korrekt bearbeiten. Bitte überprüfen Sie "
					+ "ob Sie unsere REST-API richtig verwenden oder versuchen Sie es zu einem späteren Zeitpunkt nocheinmal.");
		}
	}
}
