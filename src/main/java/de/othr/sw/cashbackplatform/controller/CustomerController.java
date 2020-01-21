package de.othr.sw.cashbackplatform.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
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
import de.othr.sw.cashbackplatform.exceptions.CategoryAlreadyRegisteredException;
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
	public String displayAccountInformation(Principal principal, Model model) {
		Customer customer = customerService.getCustomerByEmail(principal.getName());
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
										   @AuthenticationPrincipal Customer principal,
										   Model model) {
		model.addAttribute("isActive", 4);
		Customer customer = customerService.getCustomerByEmail(principal.getUsername());
		/* Update Account Information of Customer */
		try {
			// Update Email
			if (update.equals("email")) {
				String updatedEmail = customerService.updateCustomerEmail(customer, email);
				principal.setEmail(updatedEmail);
			}
			// Update Password
			if (update.equals("password")) {
				if (!password1.equals(password2)) {
					model.addAttribute("error", "Passworte stimmen nicht überein.");
				} else if (password1.isBlank() || password2.isBlank()) {
					model.addAttribute("error", "Passwortfelder dürfen nicht leer sein.");
				} else {
					String updatedPassword = customerService.updateCustomerPassword(customer, password1);
					principal.setPassword(updatedPassword);
				}
			}
			// Update Telephone Number
			if (update.equals("telephone")) {
					String updatedTelephone = customerService.updateCustomerTelephoneNumber(customer, telephone);
					principal.setTelephoneNumber(updatedTelephone);
			}
			// Update Adress
			if (update.equals("adress")) {
				Adress updatedAdress = new Adress(street, streetnumber, place, Integer.parseInt(postcode));
				updatedAdress = customerService.updateCustomerAdress(customer, updatedAdress);
				principal.setAdress(updatedAdress);
			}
			if (principal instanceof PrivateCustomer) {
				// Update Customer Name
				if (update.equals("name")) {
					Map<String, String> updatedNames = customerService.updatePrivateCustomerName((PrivateCustomer) customer, name, surname);
					((PrivateCustomer) principal).setName(updatedNames.getOrDefault("name", ((PrivateCustomer) principal).getName()));
					((PrivateCustomer) principal).setSurname(updatedNames.getOrDefault("surname", ((PrivateCustomer) principal).getSurname()));
				}
			} else if (principal instanceof Shop) {
				// Update Shopname
				if (update.equals("name")) {
					String updatedShopname = customerService.updateShopName((Shop) customer, shopname);
					((Shop) principal).setShopname(updatedShopname);
				}
				// Update Default Cashbackpoints per Sale
				if (update.equals("points")) {
					int updatedDefaultPoints = customerService.updateShopDefaultCashbackpoints((Shop) customer, Integer.parseInt(cashbackpoints));
					((Shop) principal).setDefaultCashbackPointsPerSale(updatedDefaultPoints);
				}
				// Update Shop Information
				if (update.equals("shopinfo")) {
					String updatedShopinfo = customerService.updateShopInformation((Shop) customer, shopinfo);
					((Shop) principal).setShopinfo(updatedShopinfo);
				}
				// Add Shop Categories
				if (update.equals("addcategories")) {
					List<Category> categoriesToAdd = parseCategoryList(categories, (Shop) customer);
					List<Category> updatedcategories = customerService.addShopCategories((Shop) customer, categoriesToAdd);
					((Shop) principal).setCategories(updatedcategories);
				}
			}
			if (model.getAttribute("error") == null) model.addAttribute("registration", "Änderungen erfolgreich geupdated.");
			customer = customerService.getCustomerByID(principal.getId());
			model.addAttribute("customer", customer);
			return "account";
		} catch (Exception error) {
			if (error instanceof UserAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			} else if (error instanceof CategoryAlreadyRegisteredException) {
				model.addAttribute("error", error.getMessage());
			}
			else {
				model.addAttribute("error", "Eingegebene Formulardaten sind ungültig. Bitte überprüfen Sie ob die Daten ein gültiges Format besitzen oder ob Sie alle Daten ausgefüllt haben.");
			}
			model.addAttribute("customer", principal);
			return "account";
		}
	}
	
	@RequestMapping("/account/statistics")
	public String displayStatistics(Principal principal, Model model) {
		Customer customer = customerService.getCustomerByEmail(principal.getName());
		model.addAttribute("isActive", 4);
		try {
			byte[] statisticImage = customerService.getStatisticForNumberOfCashbacksPerMonth((Shop) customer);
			// Encode byte array to Base64 String and add it as Model Attribute to display the statistic on screen
			String image = Base64Utils.encodeToString(statisticImage);
			model.addAttribute("statistic", image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "statistics";
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
			model.addAttribute("registration", "Du wurdest erfolgreich als Geschäftskunde registriert.");
			return "login";
		} catch (Exception error) {
			error.printStackTrace();
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
	
	@RequestMapping("/shops/detail/{shopid}")
	public String displayShopInformation(@PathVariable("shopid") long shopid, Model model) {
		try {
			Shop shop = customerService.getShop(shopid);
			model.addAttribute("shop", shop);
		} catch (NoSuchElementException ex) {
			model.addAttribute("shop", null);
		}
		model.addAttribute("isActive", 1);
		return "shopdetail";
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
