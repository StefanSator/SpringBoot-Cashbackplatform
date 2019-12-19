package de.othr.sw.cashbackplatform.service;

import java.util.List;

import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;

public interface CustomerServiceIF {
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException;
	public List<Customer> getAllCustomer();
}
