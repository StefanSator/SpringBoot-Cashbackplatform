package de.othr.sw.cashbackplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;

public interface CustomerRepository extends JpaRepository<Customer, String> {
	
	@Query("Select s From Shop AS s")
	List<Shop> getAllShops();
	
	@Query("Select p From PrivateCustomer AS p")
	List<PrivateCustomer> getAllPrivateCustomers();
}
