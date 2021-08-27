package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoresController {

    @Autowired
    private String googleApiKey;

    @GetMapping("/stores")
    public String showStores(Model model) {
        model.addAttribute("googleApiKey", googleApiKey);

        return "stores";
    }
}
