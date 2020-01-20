package de.othr.sw.cashbackplatform.service;

import java.util.Date;
import java.util.List;

import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CashbackServiceIF {
	public Cashback accreditCashback(PurchaseDTO purchase) throws Exception;
	public double grantMoneyForCashbackPointsOfCustomer(PrivateCustomer customer, String iban) throws Exception;
	public List<Cashback> getAllCashbacksOfPrivateCustomer(PrivateCustomer customer, Date from, Date to);
	public List<Cashback> getAllCashbacksOfShop(Shop shop, Date from, Date to);
}
