package de.othr.sw.cashbackplatform.service;

import java.util.List;

import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;

public interface CustomerServiceIF {
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException;
	public List<Shop> getAllShops();
	public Shop getShop(String email);
	public Category getShopCategory(Long categoryId);
	public List<Customer> getAllCustomer();
}
