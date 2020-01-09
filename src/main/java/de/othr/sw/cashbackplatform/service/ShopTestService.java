package de.othr.sw.cashbackplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import de.othr.sw.cashbackplatform.dto.BalanceDTO;

public class ShopTestService {
	@Autowired
	private RestTemplate restServiceClient;
	
	public void testForAccreditingCustomerAccountForPurchase() {
		
	}
	
	public void testForDebitingCustomerAccountForPurchase() {
		
	}
	
	public void testForGettingCustomerAccountBalance() {
		String accountNr = "IDMAXPRIVAT0";
		BalanceDTO balance = restServiceClient
								.getForObject("http://localhost:8080/restapi/cashback/{accountnr}", 
											  BalanceDTO.class,
											  accountNr);
		System.out.println("" + balance.getAccountIdentification() + ": " + balance.getCashbackpoints());
	}
}
