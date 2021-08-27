package com.example.demo.controllers;

import com.example.demo.model.RecentViewed;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RecentViewed recentViewed;

    @GetMapping("/product")
    public String showWelcome(@RequestParam("id") Long id, Model model) {
        var product = productService.getById(id);

        model.addAttribute("product", product);
        recentViewed.add(product);

        return "product";
    }

}
