package de.othr.sw.cashbackplatform.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.repository.CategoryRepository;
import de.othr.sw.cashbackplatform.repository.CustomerRepository;

@Service
public class CustomerService implements CustomerServiceIF, UserDetailsService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepo.findByEmail(username)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException("Kunde mit angegebener Email existiert nicht");
				});
		System.out.println(customer.getEmail());
		return customer;
	}

	@Override
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException {
		// Check if user already exists
		Customer availableCustomer = customerRepo.findByEmail(customer.getEmail()).orElse(null);
		if (availableCustomer != null) {
			throw new UserAlreadyRegisteredException("Die Email ist bereits vergeben. Bitte wählen Sie eine andere.");
		}
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer registeredCustomer = customerRepo.save(customer);
		return registeredCustomer;
	}
	
	@Override
	public Customer updateCustomerEmail(Customer customer, String email) throws UserAlreadyRegisteredException {
		// Check if user already exists
		Customer availableCustomer = customerRepo.findByEmail(email).orElse(null);
		if (availableCustomer != null) {
			throw new UserAlreadyRegisteredException("Die Email ist bereits vergeben. Bitte wählen Sie eine andere.");
		}
		customer.setEmail(email);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Customer updateCustomerPassword(Customer customer, String password) throws Exception {
		customer.setPassword(passwordEncoder.encode(password));
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Customer updateCustomerTelephoneNumber(Customer customer, String telephoneNumber) throws Exception {
		customer.setTelephoneNumber(telephoneNumber);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Customer updateCustomerAdress(Customer customer, Adress adress) {
		customer.setAdress(adress);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public PrivateCustomer updatePrivateCustomerName(PrivateCustomer customer, String name, String surname) throws Exception {
		customer.setName(name);
		customer.setSurname(surname);
		PrivateCustomer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Shop updateShopName(Shop customer, String shopname) throws Exception {
		customer.setShopname(shopname);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Shop updateShopDefaultCashbackpoints(Shop customer, int cashbackpoints) {
		customer.setDefaultCashbackPointsPerSale(cashbackpoints);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public Shop updateShopInformation(Shop customer, String shopinfo) throws Exception {
		customer.setShopinfo(shopinfo);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	// TODO: Ask Prof
	@Override
	public Shop updateShopCategories(Shop customer, List<Category> categories) {
		List<Category> prevcategories = customer.getCategories();
		for (Category category : prevcategories) {
			categoryRepo.delete(category);
		}
		customer.setCategories(categories);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer;
	}
	
	@Override
	public List<Shop> getAllShops() {
		List<Shop> shops = customerRepo.getAllShops();
		System.out.println(shops.size());
		return shops;
	}
	
	@Override
	public Shop getShop(String email) {
		Customer shop = customerRepo.findByEmail(email).orElseThrow();
		if (shop instanceof Shop) {
			return (Shop) shop;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public Category getShopCategory(Long categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow();
		return category;
	}
	
	@Override
	public Category getShopCategoryByName(String categoryName) {
		Category category = categoryRepo.findByCategory(categoryName).orElseThrow();
		return category;
	}
	
	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customer = (List<Customer>) customerRepo.findAll();
		return customer;
	}
	
	@Override
	public PrivateCustomer getPrivateCustomerWithAccountIdentification(String accountIdentification) throws Exception {
		PrivateCustomer customer = customerRepo.findByAccountIdentification(accountIdentification).orElseThrow();
		return customer;
	}

}
