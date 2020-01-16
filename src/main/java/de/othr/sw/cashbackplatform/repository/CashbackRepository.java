package de.othr.sw.cashbackplatform.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CashbackRepository extends CrudRepository<Cashback, Long> {
	List<Cashback> findByReceiverAndDateBetween(PrivateCustomer receiver, Date from, Date to);
	List<Cashback> findBySenderAndDateBetween(Shop sender, Date from, Date to);
}
