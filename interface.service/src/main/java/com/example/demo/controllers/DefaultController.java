package com.example.demo.controllers;

import com.example.demo.model.ProductWithPrice;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartImage;
import com.example.demo.model.cart.CartItem;
import com.example.demo.services.ProductService;
import lombok.SneakyThrows;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.LinkedList;

@Controller
public class DefaultController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Cart cart;

    @SneakyThrows
    @GetMapping("/")
    public String showWelcome(Model model) {
        var products = productService.getAllProducts();
        Collections.shuffle(products);
        products = products.subList(0, 1);
        model.addAttribute("products", products);

        Cart cartRes = (Cart) ((Advised) cart).getTargetSource().getTarget();
        model.addAttribute("cart", cartRes);

        return "welcome";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
