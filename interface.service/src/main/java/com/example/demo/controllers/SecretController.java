package com.example.demo.controllers;

import com.example.demo.model.RecentViewed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecretController {

    @Autowired
    private RecentViewed recentViewed;

    @GetMapping("/secret/profile")
    public String showProfile(Model model) {
        var user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("username", user.getUsername());

        var products = recentViewed.getProducts();
        model.addAttribute("products", products);

        return "profile";
    }
}
