package de.othr.sw.cashbackplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
    public String starten(Model model) {
		model.addAttribute("isActive", 0);
        return "index";
    }
}
