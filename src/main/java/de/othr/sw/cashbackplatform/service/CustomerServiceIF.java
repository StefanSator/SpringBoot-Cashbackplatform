package de.othr.sw.cashbackplatform.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.CategoryAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;

public interface CustomerServiceIF {
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException;
	public List<Shop> getAllShops();
	public Customer getCustomerByID(long id) throws NoSuchElementException;
	public Customer getCustomerByEmail(String email) throws NoSuchElementException;
	public Shop getShop(Long id) throws NoSuchElementException;
	public Shop getShop(String email) throws NoSuchElementException;
	public Category getShopCategory(Long categoryId) throws NoSuchElementException;
	public Category getShopCategory(String categoryname, Shop owner) throws NoSuchElementException;
	public List<Customer> getAllCustomer();
	public String updateCustomerEmail(Customer customer, String email) throws UserAlreadyRegisteredException;
	public String updateCustomerPassword(Customer customer, String password) throws Exception;
	public String updateCustomerTelephoneNumber(Customer customer, String telephoneNumber) throws Exception;
	public Adress updateCustomerAdress(Customer customer, Adress adress);
	public Map<String, String> updatePrivateCustomerName(PrivateCustomer customer, String name, String surname) throws Exception;
	public String updateShopName(Shop customer, String shopname) throws Exception;
	public int updateShopDefaultCashbackpoints(Shop customer, int cashbackpoints);
	public String updateShopInformation(Shop customer, String shopinfo) throws Exception;
	public List<Category> addShopCategories(Shop customer, List<Category> categories) throws CategoryAlreadyRegisteredException;
	public PrivateCustomer getPrivateCustomerWithAccountIdentification(String accountidentification) throws NoSuchElementException;
	public PrivateCustomer chargeCashbackPoints(PrivateCustomer customer, long numberOfCashbackPoints) throws Exception;
	
	public byte[] getStatisticForNumberOfCashbacksPerMonth(Shop shop) throws Exception;
}
