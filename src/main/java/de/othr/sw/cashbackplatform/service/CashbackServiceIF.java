package de.othr.sw.cashbackplatform.service;

import java.util.List;

import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;

public interface CashbackServiceIF {
	public Cashback debitCashbackAccount(PurchaseDTO purchase) throws Exception;
	public Cashback accreditCashbackAccount(PurchaseDTO purchase) throws Exception;
	public List<Cashback> getAllCashbacksOfPrivateCustomer(PrivateCustomer customer);
}
