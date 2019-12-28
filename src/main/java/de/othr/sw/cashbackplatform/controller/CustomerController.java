package de.othr.sw.cashbackplatform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
		}
		try {
			Adress adress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
			Customer customer = new PrivateCustomer(email, password, telephone, adress, surname, name);
			customer = customerService.registerCustomer(customer);
			//model.addAttribute("custemail", customer.getEmail());
			model.addAttribute("registration", "Du wurdest erfolgreich als Privatkunde registriert.");
			return "login";
		} catch (Exception error) {
			if (error instanceof UserAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			} else {
				model.addAttribute("error", 1);
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
			  				   @ModelAttribute("categories") String categories,
			  				   @ModelAttribute("cashbackpoints") String cashbackpointsPerSale,
			  				   Model model) {
		if (!password.equals(password2)) {
			model.addAttribute("error", "Passworte stimmen nicht überein.");
			return "shopregister";
		}
		try {
			Adress adress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
			Shop customer = new Shop(email, password, telephone, adress, shopname, Integer.parseInt(cashbackpointsPerSale));
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
