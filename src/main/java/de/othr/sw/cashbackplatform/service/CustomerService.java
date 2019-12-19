package de.othr.sw.cashbackplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.othr.sw.cashbackplatform.entity.Customer;
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
	public Customer registerCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer registeredCustomer = customerRepo.save(customer);
		return registeredCustomer;
	}

}
