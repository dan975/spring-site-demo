package com.example.demo.controllers;

import com.example.demo.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public String showCards(@RequestParam("criteria") String criteria, Model model) {
        var products = productService.getProductsContainingName(criteria);

        model.addAttribute("products", products);

        return "search";
    }

}
