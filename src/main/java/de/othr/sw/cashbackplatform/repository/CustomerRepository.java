package de.othr.sw.cashbackplatform.repository;

import org.springframework.data.repository.CrudRepository;

import de.othr.sw.cashbackplatform.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
