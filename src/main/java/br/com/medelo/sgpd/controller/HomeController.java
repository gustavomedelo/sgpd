package br.com.medelo.sgpd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String showHomePage(ModelMap model) {
        model.put("name", "Gustavo");
        return "home";
    }
}
