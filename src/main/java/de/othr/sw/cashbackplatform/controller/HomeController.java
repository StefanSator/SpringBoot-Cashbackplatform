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
	
	@RequestMapping("/")
    public String starten(Model model) {
		model.addAttribute("isActive", 0);
        return "index";
    }
}
