package de.othr.sw.cashbackplatform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.Cashbackposition;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.CashbackServiceException;
import de.othr.sw.cashbackplatform.repository.CashbackRepository;
import de.othr.sw.cashbackplatform.repository.CustomerRepository;

@Service
public class CashbackService implements CashbackServiceIF {
	@Autowired
	private CashbackRepository cashbackRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CustomerServiceIF customerService;
	@Autowired
	private CouponServiceIF couponService;
	@Autowired
	private PaymentServiceIF paymentService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public List<Cashback> getAllCashbacksOfPrivateCustomer(PrivateCustomer customer, Date from, Date to) {
		return cashbackRepo.findByReceiverAndDateBetween(customer, from, to);
	}
	
	@Override
	public List<Cashback> getAllCashbacksOfShop(Shop shop, Date from, Date to) {
		return cashbackRepo.findBySenderAndDateBetween(shop, from, to);
	}
	
	@Override
	@Transactional
	public double grantMoneyForCashbackPointsOfCustomer(PrivateCustomer customer, String iban) throws Exception {
		long cashbackpointsOfCustomer = ((PrivateCustomer) customer).getAccountBalance();
		double moneyValueToTransferToCustomer = cashbackpointsOfCustomer * 0.01;
		// Charge Account of Customer
		customer = customerService.chargeCashbackPoints(customer, cashbackpointsOfCustomer);
		// Transfer Money To customers bank account
		paymentService.transferMoney(iban, moneyValueToTransferToCustomer);
		return moneyValueToTransferToCustomer;
	}
	
	@Override
	@Transactional(TxType.REQUIRES_NEW)
	public Cashback accreditCashback(PurchaseDTO purchase) throws Exception {
		PrivateCustomer receiver;
		Shop sender;
		try {
			receiver = customerService.getPrivateCustomerWithAccountIdentification(purchase.getCustomerAccountIdentification());
			sender = customerService.getShop(purchase.getShopEmail());
		} catch (NoSuchElementException ex) {
			throw new CashbackServiceException("Zugriff verweigert, da der Privatkunde oder Shop unserem System nicht bekannt ist.");
		}
		if (!passwordEncoder.matches(purchase.getCustomerPassword(), receiver.getPassword())
				|| !passwordEncoder.matches(purchase.getShopPassword(), sender.getPassword())) {
			throw new CashbackServiceException("Zugriff verweigert, da Authentifizierung fehlgeschlagen.");
		}
		List<Coupon> currentShopCoupons = couponService.getAllCurrentCouponsOfShop(purchase.getDate(), sender);
		List<Cashbackposition> cashbackpositions = new ArrayList<>();
		Set<String> categories = purchase.getPrices().keySet();
		for (String category : categories) {
			Category cashbackCategory;
			try {
				cashbackCategory = customerService.getShopCategory(category, sender);
			} catch (NoSuchElementException ex) {
				throw new CashbackServiceException("Ungültige Kategorien, bitte überprüfen Sie ob Sie die richtigen uns bekannten Kategorien angegeben haben.");
			}
			double salesprice = purchase.getPrices().get(category);
			// Check if a coupon for this category is active
			Coupon couponForCategory = currentShopCoupons.stream()
					.filter(coupon -> coupon.getCouponCategory().equals(cashbackCategory))        
					.findAny()                                     
					.orElse(null);
			long cashbackpoints;
			if (couponForCategory == null) {
				cashbackpoints = Math.round(salesprice) * sender.getDefaultCashbackPointsPerSale();
			} else {
				cashbackpoints = Math.round(salesprice) * sender.getDefaultCashbackPointsPerSale() * couponForCategory.getCashbackPointsMultiplicator();
			}
			cashbackpositions.add(new Cashbackposition((int) cashbackpoints, cashbackCategory));
			receiver.addToAccountBalance(cashbackpoints);
		}
		Cashback cashback = new Cashback(purchase.getDate(), purchase.getPurchaseIdentification(), cashbackpositions, receiver, sender, true);
		cashbackRepo.save(cashback);
		customerRepo.save(receiver);
		
		return cashback;
	}
}
