package de.othr.sw.cashbackplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.othr.sw.cashbackplatform.entity.Kunde;
import de.othr.sw.cashbackplatform.service.KundeServiceIF;

@Controller
public class HomeController {
	@Autowired
	private KundeServiceIF kundeService;
	
	@RequestMapping("/")
    public String starten(Model model) {
		model.addAttribute("isActive", 0);
        return "index";
    }
	
	@RequestMapping("/login")
    public String loginUser() {
        return "login";
    }
	
	@RequestMapping("/coupon")
    public String coupon() {
        return "coupon";
    }
	
	@RequestMapping("/registrieren")
	public String registrieren(@ModelAttribute("vorname") String vorname,
							   @ModelAttribute("nachname") String nachname,
							   Model model) {
		Kunde kunde = new Kunde();
		kunde.setVorname(vorname);
		kunde.setNachname(nachname);
		kunde = kundeService.kundeAnlegen(kunde);
		model.addAttribute("kundennr", kunde.getKundenNr());
		return "kundenkonto";
	}
}
