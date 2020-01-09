package de.othr.sw.cashbackplatform.service;

import de.othr.sw.cashbackplatform.dto.PurchaseDTO;
import de.othr.sw.cashbackplatform.entity.Cashback;

public interface CashbackServiceIF {
	public Cashback debitCashbackAccount(PurchaseDTO purchase) throws Exception;
	public Cashback accreditCashbackAccount(PurchaseDTO purchase) throws Exception;
}
