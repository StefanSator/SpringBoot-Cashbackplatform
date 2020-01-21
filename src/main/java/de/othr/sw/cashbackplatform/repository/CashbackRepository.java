package de.othr.sw.cashbackplatform.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.sw.cashbackplatform.entity.Cashback;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CashbackRepository extends JpaRepository<Cashback, Long> {
	List<Cashback> findByReceiverAndDateBetweenOrderByDateDesc(PrivateCustomer receiver, Date from, Date to);
	List<Cashback> findBySenderAndDateBetweenOrderByDateDesc(Shop sender, Date from, Date to);
	long countBySenderAndDateBetween(Shop sender, Date from, Date to);
}
