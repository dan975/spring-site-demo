package com.example.demo.controllers;

import com.example.demo.model.cart.Cart;
import com.example.demo.model.cart.CartAdditionResponse;
import com.example.demo.model.cart.CartImage;
import com.example.demo.model.cart.CartItem;
import com.example.demo.services.ProductService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Controller
@RequestMapping("cart")
public class CartController {

    @Getter(AccessLevel.PRIVATE)
    @Autowired
    private Cart cart;

    @Autowired
    private ProductService productService;

    @SneakyThrows
    @GetMapping("/")
    private String showCart(Model model) {
        var res = (Cart) ((Advised) cart).getTargetSource().getTarget();
        model.addAttribute("cart", res);

        return "fragments/cart/cart :: cartMenu";
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/add")
    public CartAdditionResponse addToCart(@RequestParam long id, @RequestParam(required = false, defaultValue = "1") int count) {
        //Intentional wait to give time for spinner to appear when loading product addition screen
        // for demonstration purposes only
        Thread.sleep(500 + new Random().nextInt(3_500));

        var product = productService.getById(id);

        var res = CartItem.builder()
                .id(id)
                .image(new CartImage(product.getMainImage().getImage()))
                .count(count)
                .color("Black")
                //TODO size ignored for now
                .size("Medium")
                .price(product.getPrice())
                .name(product.getProductName())
                .build();
        cart.add(res);

        var updatedCart = (Cart) ((Advised) cart).getTargetSource().getTarget();

        return new CartAdditionResponse(res, updatedCart);
    }

    @DeleteMapping
    @ResponseBody
    public void delete(@RequestParam long id) {
        cart.remove(id);
    }
}
