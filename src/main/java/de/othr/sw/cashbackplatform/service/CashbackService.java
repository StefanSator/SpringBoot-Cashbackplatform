package de.othr.sw.cashbackplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.Cashbackposition;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.repository.CashbackRepository;
import de.othr.sw.cashbackplatform.repository.CustomerRepository;

@Service
public class CashbackService implements CashbackServiceIF {
	@Autowired
	private CashbackRepository cashbackRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CustomerService customerService;
	
	// TODO
	
	@Override
	public Cashback debitCashbackAccount(PurchaseDTO purchase) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Debit Account.");
		return null;
	}
	
	@Override
	@Transactional(TxType.MANDATORY)
	public Cashback accreditCashbackAccount(PurchaseDTO purchase) throws Exception {
		System.out.println("Accredit Account.");
		PrivateCustomer receiver = customerRepo.findByAccountIdentification(purchase.getCustomerAccountIdentification()).orElseThrow();
		Shop sender = (Shop) customerRepo.findByEmail(purchase.getShopEmail()).orElseThrow();
		if (receiver.getPassword() != purchase.getCustomerPassword() || sender.getPassword() != purchase.getShopPassword()) {
			throw new Exception("Zugriff verweigert, da Authentifizierung fehlgeschlagen.");
		}
		List<Cashbackposition> cashbackpositions = new ArrayList<>();
		Set<String> categories = purchase.getPrices().keySet();
		for (String category : categories) {
			Category cashbackCategory = customerService.getShopCategoryByName(category);
			double salesprice = purchase.getPrices().get(category);
			long cashbackpoints = Math.round(salesprice) * sender.getDefaultCashbackPointsPerSale();
			cashbackpositions.add(new Cashbackposition((int) cashbackpoints, cashbackCategory));
			receiver.addToAccountBalance(cashbackpoints);
		}
		Cashback cashback = new Cashback(purchase.getDate(), purchase.getPurchaseIdentification(), cashbackpositions, receiver, sender, true);
		cashbackRepo.save(cashback);
		customerRepo.save(receiver);
		
		return cashback;
	}
}
