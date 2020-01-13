package de.othr.sw.cashbackplatform.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CashbackRepository extends CrudRepository<Cashback, Long> {
	List<Cashback> findByReceiver(PrivateCustomer receiver);
	List<Cashback> findBySender(Shop sender);
}
