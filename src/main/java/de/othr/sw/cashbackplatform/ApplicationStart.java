package de.othr.sw.cashbackplatform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.othr.sw.cashbackplatform.entity.Adress;
import de.othr.sw.cashbackplatform.entity.Category;
import de.othr.sw.cashbackplatform.entity.Coupon;
import de.othr.sw.cashbackplatform.entity.Customer;
import de.othr.sw.cashbackplatform.entity.PrivateCustomer;
import de.othr.sw.cashbackplatform.entity.Shop;
import de.othr.sw.cashbackplatform.service.CouponServiceIF;
import de.othr.sw.cashbackplatform.service.CustomerServiceIF;
import de.othr.sw.cashbackplatform.service.ShopTestService;

@SpringBootApplication
public class ApplicationStart implements CommandLineRunner {
	@Autowired
	private CustomerServiceIF customerService;
	@Autowired
	private CouponServiceIF couponService;
	@Autowired
	private ShopTestService cashbackTestService;
	@Value("${application-config.firststart}")
	private boolean firststart;

	@Override
	public void run(String... args) throws Exception {
		if (firststart != true) {
			return;
		}
		/* Generate Users */
		// Generate Shop Customers
		// Shop 1
		Adress adress1 = new Adress("Musterstr.", "1", "Musterstadt", 99999);
		String shopinfo = "Wir sind ein Lebensmittel-Discounter der es sich ans Herz gelegt hat unseren Kunden die möglichst besten Lebensmittel anzubieten"
			+ " und so für Ihre optimale Kundenzufriedenheit zu sorgen.";
		Shop shop1 = new Shop("max.muster1@email.de", "1234", "09999999999", adress1, "Maxi Discounter", shopinfo, 1);
		List<Category> shopcategories = new ArrayList<>();
		shopcategories.add(new Category("Obst", shop1));
		shopcategories.add(new Category("Gemüse", shop1));
		shopcategories.add(new Category("Kühlwaren", shop1));
		shopcategories.add(new Category("Fleischwaren", shop1));
		shop1.setCategories(shopcategories);
		shop1 = (Shop) customerService.registerCustomer(shop1);
		// Shop 2
		Adress adress2 = new Adress("Musterstr.", "2", "Musterstadt", 99999);
		String shopinfo2 = "Wir von Elec Markt sind ein national sehr erfolgreicher Elektronikmarkt der stets versucht seinen Kunden mit Rat und Tat zur Seite zu stehen"
			+ ", um Ihnen so das optimale Verkaufserlebnis zu gewährleisten.";
		Shop shop2 = new Shop("max.muster2@email.de", "1234", "09999999999", adress2, "Elec Markt", shopinfo2, 3);
		List<Category> shopcategories2 = new ArrayList<>();
		shopcategories2.add(new Category("Videospiele und Konsolen", shop2));
		shopcategories2.add(new Category("Filme", shop2));
		shopcategories2.add(new Category("Musik", shop2));
		shopcategories2.add(new Category("TV und Zubehör", shop2));
		shop2.setCategories(shopcategories2);
		shop2 = (Shop) customerService.registerCustomer(shop2);
		// Shop 3
		Adress adress3 = new Adress("Musterstr.", "3", "Musterstadt", 99999);
		String shopinfo3 = "Als erfolgreicher Schuhladen möchten wir unseren Kunden stets die besten Preise und die beste Qualität rund um Schuhe anbieten."
			+ " Wir freuen uns sehr wenn Sie uns eine Chance geben und wir sie bald als Kunde begrüßen dürfen";
		Shop shop3 = new Shop("max.muster3@email.de", "1234", "09999999999", adress3, "Shoe Paradise", shopinfo3, 2);
		List<Category> shopcategories3 = new ArrayList<>();
		shopcategories3.add(new Category("Damenschuhe", shop3));
		shopcategories3.add(new Category("Herrenschuhe", shop3));
		shopcategories3.add(new Category("Kinderschuhe", shop3));
		shop3.setCategories(shopcategories3);
		shop3 = (Shop) customerService.registerCustomer(shop3);
		// Generate a default private Customer
		Adress adress4 = new Adress("Musterstr.", "4", "Musterstadt", 99999);
		Customer privateCustomer = new PrivateCustomer("max.private@email.de", "1234", "09999999999", adress4, "Max", "Privat", 0);
		privateCustomer = customerService.registerCustomer(privateCustomer);
		System.out.println("Customer Accounts created.");
		
		/* Generate eCoupons */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Coupon 1
		Date begin1 = formatter.parse("2019-12-10");
		Date end1 = formatter.parse("2020-02-25");
		Category cat1 = shop1.getCategory(0);
		Coupon coupon1 = new Coupon("Jahresendeverkauf", begin1, end1, 2, cat1, shop1);
		coupon1 = couponService.registerCoupon(coupon1);
		// Coupon 2
		Date begin2 = formatter.parse("2020-02-01");
		Date end2 = formatter.parse("2020-03-01");
		Category cat2 = shop2.getCategory(0);
		Coupon coupon2 = new Coupon("Happy New Year", begin2, end2, 5, cat2, shop2);
		coupon2 = couponService.registerCoupon(coupon2);
		// Coupon 3
		Date begin3 = formatter.parse("2019-12-10");
		Date end3 = formatter.parse("2020-03-01");
		Category cat3 = shop3.getCategory(0);
		Coupon coupon3 = new Coupon("Sale", begin3, end3, 3, cat3, shop3);
		coupon3 = couponService.registerCoupon(coupon3);
		// Coupon 4
		Date begin4 = formatter.parse("2020-04-01");
		Date end4 = formatter.parse("2020-05-01");
		Category cat4 = shop1.getCategory(2);
		Coupon coupon4 = new Coupon("Oster Sale", begin4, end4, 2, cat4, shop1);
		coupon4 = couponService.registerCoupon(coupon4);
		// Coupon 5
		Date begin5 = formatter.parse("2020-03-01");
		Date end5 = formatter.parse("2020-06-01");
		Category cat5 = shop2.getCategory(1);
		Coupon coupon5 = new Coupon("Halbjahrsangebot", begin5, end5, 3, cat5, shop2);
		coupon5 = couponService.registerCoupon(coupon5);
		System.out.println("Coupons created.");
		// Choose at beginning daily recommendation
		couponService.recommendRandomDailyCoupon();
		System.out.println("Daily Recommendation saved.");
		
		/* Generate Cashback from Online Shop */
		cashbackTestService.testForGettingCustomerAccountBalance((PrivateCustomer) privateCustomer);
		cashbackTestService.testForAccreditingCustomerAccountForPurchase((PrivateCustomer) privateCustomer, "1234", shop1, "1234");
		cashbackTestService.testForAccreditingCustomerAccountForPurchase((PrivateCustomer) privateCustomer, "1234", shop2, "1234");
		cashbackTestService.testForAccreditingCustomerAccountForPurchase((PrivateCustomer) privateCustomer, "1234", shop3, "1234");
		cashbackTestService.testForGettingCustomerAccountBalance((PrivateCustomer) privateCustomer);
		System.out.println("Cashback for Purchase accredited.");
		System.out.println("Application Initialization completed.");
	}

}
