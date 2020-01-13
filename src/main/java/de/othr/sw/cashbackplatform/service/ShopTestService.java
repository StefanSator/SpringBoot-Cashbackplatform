package de.othr.sw.cashbackplatform.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.othr.sw.cashbackplatform.dto.BalanceDTO;
import de.othr.sw.cashbackplatform.dto.CashbackDTO;
import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

@Service
public class ShopTestService {
	@Autowired
	private RestTemplate restServiceClient;
	
	public void testForAccreditingCustomerAccountForPurchase(PrivateCustomer customer, String custpassword, Shop shop, String shoppassword) {
		/* Pass randomly generated Purchases to the Rest API to grant customer with cashbackpoints for the purchases */
		Date purchaseDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(purchaseDate);
		String customerAccountIdentification = customer.getAccountIdentification();
		String shopEmail = shop.getEmail();
		double totalprice = 0.0;
		for (int i = 0 ; i < 20 ; i++) {
			calendar.add(Calendar.DATE, 5);
			String purchaseIdentification = "123-123-12" + i;
			Map<String, Double> prices = new HashMap<String, Double>();
			for (Category category : shop.getCategories()) {
				double start = 1;
				double end = 150;
				double random = new Random().nextDouble();
				double randomPrice = start + (random * (end - start));
				prices.put(category.getCategory(), randomPrice);
				totalprice += randomPrice;
			}
			PurchaseDTO purchase = new PurchaseDTO(calendar.getTime(), purchaseIdentification, customerAccountIdentification, custpassword,
					   shopEmail, shoppassword, prices, totalprice);
			CashbackDTO cashback = restServiceClient
									.postForObject("http://localhost:8080/restapi/cashback/accredit", 
												   purchase, 
												   CashbackDTO.class);
			System.out.println("Account mit Nummer " + cashback.getAccountIdentification() + " " + cashback.getCashbackpoints() + " Punkte erfolgreich hinzugefügt.");
		}
	}
	
	public void testForDebitingCustomerAccountForPurchase() {
		// TODO
	}
	
	public void testForGettingCustomerAccountBalance(PrivateCustomer customer) {
		String accountIdentification = customer.getAccountIdentification();
		BalanceDTO balance = restServiceClient
								.getForObject("http://localhost:8080/restapi/cashback/{accountnr}", 
											  BalanceDTO.class,
											  accountIdentification);
		System.out.println("" + balance.getAccountIdentification() + ": " + balance.getCashbackpoints());
	}
}
