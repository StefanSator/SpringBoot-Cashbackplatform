package de.othr.sw.cashbackplatform.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.othr.sw.cashbackplatform.dto.CashbackDTO;
import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.BusinessObject;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.BusinessObjectDTO;
import de.othr.sw.cashbackplatform.entity.statisticrestservice.StatisticPackageDTO;
import de.othr.sw.cashbackplatform.exceptions.CategoryAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.proxy.StatisticsProxyIF;
import de.othr.sw.cashbackplatform.repository.CashbackRepository;
import de.othr.sw.cashbackplatform.repository.CategoryRepository;
import de.othr.sw.cashbackplatform.repository.CustomerRepository;

@Service
public class CustomerService implements CustomerServiceIF, UserDetailsService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private CashbackRepository cashbackRepo;
	@Autowired
	@Qualifier("test")
	private StatisticsProxyIF statisticsProxy;
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
	public String updateCustomerEmail(Customer customer, String email) throws UserAlreadyRegisteredException {
		// Check if user already exists
		Customer availableCustomer = customerRepo.findByEmail(email).orElse(null);
		if (availableCustomer != null) {
			throw new UserAlreadyRegisteredException("Die Email ist bereits vergeben. Bitte wählen Sie eine andere.");
		}
		customer.setEmail(email);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getEmail();
	}
	
	@Override
	public String updateCustomerPassword(Customer customer, String password) throws Exception {
		customer.setPassword(passwordEncoder.encode(password));
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getPassword();
	}
	
	@Override
	public String updateCustomerTelephoneNumber(Customer customer, String telephoneNumber) throws Exception {
		customer.setTelephoneNumber(telephoneNumber);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getTelephoneNumber();
	}
	
	@Override
	public Adress updateCustomerAdress(Customer customer, Adress adress) {
		customer.setAdress(adress);
		Customer updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getAdress();
	}
	
	@Override
	public Map<String, String> updatePrivateCustomerName(PrivateCustomer customer, String name, String surname) throws Exception {
		customer.setName(name);
		customer.setSurname(surname);
		PrivateCustomer updatedCustomer = customerRepo.save(customer);
		HashMap<String, String> names = new HashMap<>();
		names.put("name", updatedCustomer.getName());
		names.put("surname", updatedCustomer.getSurname());
		return names; 
	}
	
	@Override
	public String updateShopName(Shop customer, String shopname) throws Exception {
		customer.setShopname(shopname);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getShopname();
	}
	
	@Override
	public int updateShopDefaultCashbackpoints(Shop customer, int cashbackpoints) {
		customer.setDefaultCashbackPointsPerSale(cashbackpoints);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getDefaultCashbackPointsPerSale();
	}
	
	@Override
	public String updateShopInformation(Shop customer, String shopinfo) throws Exception {
		customer.setShopinfo(shopinfo);
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getShopinfo();
	}
	
	@Override
	public List<Category> addShopCategories(Shop customer, List<Category> categories) throws CategoryAlreadyRegisteredException {
		boolean duplicates = categories.stream().anyMatch(category -> customer.getCategories().contains(category));
		System.out.println("duplicates: " + duplicates);
		if (duplicates) throw new CategoryAlreadyRegisteredException("Duplikate vorhanden. Bitte achten Sie darauf, dass Sie keine Kategorien hinzufügen wollen, die bereits vorhanden sind.");
		for (Category category : categories) {
			customer.appendCategory(category);
		}
		Shop updatedCustomer = customerRepo.save(customer);
		return updatedCustomer.getCategories();
	}
	
	@Override
	public Customer getCustomerByID(long id) throws NoSuchElementException {
		Customer customer = customerRepo.findById(id).orElseThrow(() -> {
			throw new NoSuchElementException("Kunde mit angegebener ID existiert nicht!");
		});
		return customer;
	}
	
	@Override
	public Customer getCustomerByEmail(String email) throws NoSuchElementException {
		Customer customer = customerRepo.findByEmail(email).orElseThrow(() -> {
			throw new NoSuchElementException("Kunde mit angegebener Email existiert nicht!");
		});
		return customer;
	}
	
	@Override
	public List<Shop> getAllShops() {
		List<Shop> shops = customerRepo.getAllShops();
		return shops;
	}
	
	@Override
	public Shop getShop(Long id) throws NoSuchElementException {
		Customer shop = customerRepo.findById(id).orElseThrow(() -> {
			throw new NoSuchElementException();
		});
		if (shop instanceof Shop) {
			return (Shop) shop;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public Shop getShop(String email) throws NoSuchElementException {
		Customer shop = customerRepo.findByEmail(email).orElseThrow(() -> {
			throw new NoSuchElementException();
		});
		if (shop instanceof Shop) {
			return (Shop) shop;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public Category getShopCategory(Long categoryId) throws NoSuchElementException {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> {
			throw new NoSuchElementException("Kategorie existiert nicht in unserem System.");
		});
		return category;
	}
	
	@Override
	public Category getShopCategory(String categoryName, Shop owner) throws NoSuchElementException {
		Category category = categoryRepo.findByCategoryAndOwner(categoryName, owner).orElseThrow(() -> {
			throw new NoSuchElementException("Kategorie existiert nicht in unserem System.");
		});
		return category;
	}
	
	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customer = (List<Customer>) customerRepo.findAll();
		return customer;
	}
	
	@Override
	public PrivateCustomer getPrivateCustomerWithAccountIdentification(String accountIdentification) throws NoSuchElementException {
		PrivateCustomer customer = customerRepo.findByAccountIdentification(accountIdentification).orElseThrow(() -> {
			throw new NoSuchElementException("Privatkunde mit dieser Kontoidentifikation existiert nicht.");
		});
		return customer;
	}
	
	@Override
	@Transactional
	public PrivateCustomer chargeCashbackPoints(PrivateCustomer customer, long numberOfCashbackPoints) throws Exception {
		customer.subtractFromAccountBalance(numberOfCashbackPoints);
		if (customer.getAccountBalance() < 0) {
			throw new Exception("Privatkunde hat nicht genug Punkte auf dem Konto.");
		}
		customer = customerRepo.save(customer);
		return customer;
	}
	
	@Override
	public byte[] getStatisticForNumberOfCashbacksPerMonth(Shop shop) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		// Get the count of Cashbacks Per Month of the current year for shop
		List<Long> cashbacksPerMonthInCurrentYear = new ArrayList<>();
		for (int i = 0 ; i < 12 ; i++) {
			Date from = calendar.getTime();
			calendar.add(Calendar.MONTH, 1);
			Date to = calendar.getTime();
			cashbacksPerMonthInCurrentYear.add(cashbackRepo.countBySenderAndDateBetween(shop, from, to));
		}
		// Send Data to Statistic Service and receive Statistic which displays number of cashbacks per month of the shop
		BusinessObjectDTO dto = new BusinessObjectDTO();
		for (int i = 0 ; i < 12 ; i++) {
			BusinessObject businessObject = new BusinessObject();
	        businessObject.setDataModelId(61L);
	        businessObject.setCustomerId(77L);
	        businessObject.addAttribute("countofcashbacks", cashbacksPerMonthInCurrentYear.get(i));
	        businessObject.addAttribute("month", i + 1);
	        dto.addBusinessObject(businessObject);
		}
        dto.addStatisticStructureID(58L);
        
        StatisticPackageDTO statisticsPackage = statisticsProxy.sendBusinessObjectsAndReceiveStatisticPackageDTO(dto);
        System.out.println(statisticsPackage);
        Map<Long, byte[]> statistics = statisticsPackage.getStatisticAsByteArraysMap();
        return statistics.get(58L);
	}

}
