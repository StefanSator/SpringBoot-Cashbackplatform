package de.othr.sw.cashbackplatform.service;

import java.util.List;

import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;

public interface CustomerServiceIF {
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException;
	public List<Shop> getAllShops();
	public Shop getShop(String email);
	public Category getShopCategory(Long categoryId);
	public Category getShopCategoryByName(String categoryname);
	public List<Customer> getAllCustomer();
	public Customer updateCustomerEmail(Customer customer, String email) throws UserAlreadyRegisteredException;
	public Customer updateCustomerPassword(Customer customer, String password) throws Exception;
	public Customer updateCustomerTelephoneNumber(Customer customer, String telephoneNumber) throws Exception;
	public Customer updateCustomerAdress(Customer customer, Adress adress);
	public PrivateCustomer updatePrivateCustomerName(PrivateCustomer customer, String name, String surname) throws Exception;
	public Shop updateShopName(Shop customer, String shopname) throws Exception;
	public Shop updateShopDefaultCashbackpoints(Shop customer, int cashbackpoints);
	public Shop updateShopInformation(Shop customer, String shopinfo) throws Exception;
	public Shop updateShopCategories(Shop customer, List<Category> categories);
	public PrivateCustomer getPrivateCustomerWithAccountIdentification(String accountidentification) throws Exception;
}
