package de.othr.sw.cashbackplatform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.exceptions.UserAlreadyRegisteredException;
import de.othr.sw.cashbackplatform.service.CustomerServiceIF;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerServiceIF customerService;
	
	@RequestMapping("/login")
    public String loginUser() {
        return "login";
    }
	
	@RequestMapping("/logout")
	public String logoutUser() {
		return "login";
	}
	
	@RequestMapping(value = "/account", method=RequestMethod.GET)
	public String displayAccountInformation(@AuthenticationPrincipal Customer customer, Model model) {
		model.addAttribute("isActive", 4);
		if (customer instanceof PrivateCustomer) {
			model.addAttribute("customer", (PrivateCustomer) customer);
		} else {
			model.addAttribute("customer", (Shop) customer);
		}
		return "account";
	}
	
	@RequestMapping(value = "/account", method=RequestMethod.POST)
	public String updateAccountInformation(@RequestParam(name = "update") String update,
										   @ModelAttribute("email") String email,
										   @ModelAttribute("password") String password1,
										   @ModelAttribute("passwordcheck") String password2,
										   @ModelAttribute("telephone") String telephone,
										   @ModelAttribute("street") String street,
										   @ModelAttribute("streetnumber") String streetnumber,
										   @ModelAttribute("postcode") String postcode,
										   @ModelAttribute("place") String place,
										   @ModelAttribute("surname") String surname,
										   @ModelAttribute("name") String name,
										   @ModelAttribute("shopname") String shopname,
										   @ModelAttribute("cashbackpoints") String cashbackpoints,
										   @ModelAttribute("shopinfo") String shopinfo,
										   @ModelAttribute("categories") String categories,
										   @AuthenticationPrincipal Customer customer,
										   Model model) {
		model.addAttribute("isActive", 4);
		/* Update Account Information of Customer */
		try {
			// Update Email
			if (update.equals("email")) {
				customer = customerService.updateCustomerEmail(customer, email);
			}
			// Update Password
			if (update.equals("password")) {
				if (!password1.equals(password2)) {
					model.addAttribute("error", "Passworte stimmen nicht überein.");
				} else if (password1.isBlank() || password2.isBlank()) {
					model.addAttribute("error", "Passwortfelder dürfen nicht leer sein.");
				} else {
					customer = customerService.updateCustomerPassword(customer, password1);
				}
			}
			// Update Telephone Number
			if (update.equals("telephone")) {
					customer = customerService.updateCustomerTelephoneNumber(customer, telephone);	
			}
			// Update Adress
			if (update.equals("adress")) {
				Adress updatedAdress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
				customer = customerService.updateCustomerAdress(customer, updatedAdress);
			}
			if (customer instanceof PrivateCustomer) {
				// Update Customer Name
				if (update.equals("name")) {
					customer = customerService.updatePrivateCustomerName((PrivateCustomer) customer, name, surname);
				}
			} else if (customer instanceof Shop) {
				customer = (Shop) customer;
				// Update Shopname
				if (update.equals("name")) {
					customer = customerService.updateShopName((Shop) customer, shopname);
				}
				// Update Default Cashbackpoints per Sale
				if (update.equals("points")) {
					customer = customerService.updateShopDefaultCashbackpoints((Shop) customer, Integer.parseInt(cashbackpoints));
				}
				// Update Shop Information
				if (update.equals("shopinfo")) {
					customer = customerService.updateShopInformation((Shop) customer, shopinfo);
				}
				// Update Shop Categories
				if (update.equals("categories")) {
					List<Category> shopcategories = parseCategoryList(categories, (Shop) customer);
					customer = customerService.updateShopCategories((Shop) customer, shopcategories);
				}
			}
			if (model.getAttribute("error") == null) model.addAttribute("registration", "Änderungen erfolgreich geupdated.");
			model.addAttribute("customer", customer);
			return "account";
		} catch (Exception error) {
			if (error instanceof UserAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			} else {
				model.addAttribute("error", "Eingegebene Formulardaten sind ungültig. Bitte überprüfen Sie ob die Daten ein gültiges Format besitzen oder ob Sie alle Daten ausgefüllt haben.");
			}
			model.addAttribute("customer", customer);
			error.printStackTrace();
			return "account";
		}
	}
	
	@RequestMapping("/new")
	public String displayRegisterWindow() {
		return "register";
	}
	
	@RequestMapping("/new/privatecustomer")
	public String displayPrivateCustomerRegisterForm() {
		return "privateregister";
	}
	
	@RequestMapping("new/privatecustomer/register")
	public String registerPrivateCustomer(@ModelAttribute("surname") String surname,
										  @ModelAttribute("name") String name,
										  @ModelAttribute("telephone") String telephone,
										  @ModelAttribute("street") String street,
										  @ModelAttribute("streetnumber") String streetnumber,
										  @ModelAttribute("postcode") String postcode,
										  @ModelAttribute("place") String place,
										  @ModelAttribute("email") String email,
										  @ModelAttribute("password") String password,
										  @ModelAttribute("password2") String password2,
										  Model model) {
		if (!password.equals(password2)) {
			model.addAttribute("error", "Passworte stimmen nicht überein.");
			return "privateregister";
		} else if (password.isBlank() || password2.isBlank()) {
			model.addAttribute("error", "Passwortfelder dürfen nicht leer sein.");
			return "privateregister";
		}
		try {
			Adress adress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
			Customer customer = new PrivateCustomer(email, password, telephone, adress, surname, name, 0);
			customer = customerService.registerCustomer(customer);
			//model.addAttribute("custemail", customer.getEmail());
			model.addAttribute("registration", "Du wurdest erfolgreich als Privatkunde registriert.");
			return "login";
		} catch (Exception error) {
			if (error instanceof UserAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			} else {
				model.addAttribute("error", "Eingegebene Formulardaten sind ungültig. Bitte überprüfen Sie ob die Daten ein gültiges Format besitzen oder ob Sie alle Daten ausgefüllt haben.");
			}
			return "privateregister";
		}
	}
	
	@RequestMapping("/new/shop")
	public String displayShopRegisterForm() {
		return "shopregister";
	}
	
	@RequestMapping("/new/shop/register")
	public String registerShop(@ModelAttribute("shopname") String shopname,
			  				   @ModelAttribute("telephone") String telephone,
			  				   @ModelAttribute("street") String street,
			  				   @ModelAttribute("streetnumber") String streetnumber,
			  				   @ModelAttribute("postcode") String postcode,
			  				   @ModelAttribute("place") String place,
			  				   @ModelAttribute("email") String email,
			  				   @ModelAttribute("password") String password,
			  				   @ModelAttribute("password2") String password2,
			  				   @ModelAttribute("shopinfo") String shopinfo,
			  				   @ModelAttribute("categories") String categories,
			  				   @ModelAttribute("cashbackpoints") String cashbackpointsPerSale,
			  				   Model model) {
		if (!password.equals(password2)) {
			model.addAttribute("error", "Passworte stimmen nicht überein.");
			return "shopregister";
		} else if (password.isBlank() || password2.isBlank()) {
			model.addAttribute("error", "Passwortfelder dürfen nicht leer sein.");
			return "shopregister";
		}
		try {
			Adress adress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
			Shop customer = new Shop(email, password, telephone, adress, shopname, shopinfo, Integer.parseInt(cashbackpointsPerSale));
			List<Category> shopcategories = parseCategoryList(categories, customer);
			customer.setCategories(shopcategories);
			customer = (Shop) customerService.registerCustomer(customer);
			//model.addAttribute("custemail", customer.getEmail());
			//model.addAttribute("custcategories", customer.getCategories());
			model.addAttribute("registration", "Du wurdest erfolgreich als Geschäftskunde registriert.");
			return "login";
		} catch (Exception error) {
			if (error instanceof UserAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			} else {
				model.addAttribute("error", "Eingegebene Formulardaten sind ungültig. Bitte überprüfen Sie ob die Daten ein gültiges Format besitzen oder ob Sie alle Daten ausgefüllt haben.");
			}
			return "shopregister";
		}
	}
	
	@RequestMapping("/shops")
	public String displayShopCatalog(Model model) {
		List<Shop> shops = customerService.getAllShops();
		for (Shop shop : shops) {
			System.out.println(shop.getShopname());
		}
		model.addAttribute("shops", shops);
		model.addAttribute("isActive", 1);
		return "shopcatalog";
	}
	
	@RequestMapping("/shops/detail/{email}")
	public String displayShopInformation(@PathVariable("email") String email, Model model) {
		try {
			Shop shop = customerService.getShop(email);
			model.addAttribute("shop", shop);
		} catch (NoSuchElementException ex) {
			model.addAttribute("shop", null);
		}
		model.addAttribute("isActive", 1);
		return "shopdetail";
	}
	
	@RequestMapping("/getall")
	public String getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomer();
		for (Customer customer : customers) {
			System.out.println(customer.getEmail());
			System.out.println(customer instanceof Shop);
			System.out.println(customer instanceof PrivateCustomer);
		}
		return "index";
	}
	
	// Private Functions
	private List<Category> parseCategoryList(String categories, Shop owner) {
		String[] categoryNames = categories.split("[\r\n]+");
		List<Category> categoryList = new ArrayList<>();
		for (int i = 0 ; i < categoryNames.length ; i++) {
			categoryList.add(new Category(categoryNames[i], owner));
		}
		return categoryList;
	}
}
