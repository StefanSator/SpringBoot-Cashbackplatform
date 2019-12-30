package de.othr.sw.cashbackplatform.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.repository.CustomerRepository;

@Service
public class CustomerService implements CustomerServiceIF, UserDetailsService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepo.findById(username)
				.orElseThrow(() -> {
					throw new UsernameNotFoundException("Kunde mit angegebener Email existiert nicht");
				});
		System.out.println(customer.getEmail());
		return customer;
	}

	@Override
	public Customer registerCustomer(Customer customer) throws UserAlreadyRegisteredException {
		// Check if user already exists
		Customer availableCustomer = customerRepo.findById(customer.getEmail()).orElse(null);
		if (availableCustomer != null) {
			throw new UserAlreadyRegisteredException("Die Email ist bereits vergeben. Bitte wählen Sie eine andere.");
		}
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer registeredCustomer = customerRepo.save(customer);
		return registeredCustomer;
	}
	
	@Override
	public List<Shop> getAllShops() {
		List<Shop> shops = customerRepo.getAllShops();
		System.out.println(shops.size());
		return shops;
	}
	
	@Override
	public Shop getShop(String email) {
		Customer shop = customerRepo.findById(email).orElseThrow();
		if (shop instanceof Shop) {
			return (Shop) shop;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customer = (List<Customer>) customerRepo.findAll();
		return customer;
	}

}
