package com.example.demo.controllers;

import com.example.demo.model.CatalogGrouping;
import com.example.demo.model.RecentViewed;
import com.example.demo.model.cart.Cart;
import com.example.demo.model.category.CategoryFilterRequest;
import com.example.demo.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatalogController {
    private static final Crumb HOME = new Crumb("Home", "/");
    private static final Crumb CATALOG = new Crumb("Catalog", "/catalog");

    @Autowired
    private ProductService productService;

    @Autowired
    private RecentViewed recentViewed;

    @Autowired
    private CatalogGrouping grouping;

    @Autowired
    private Cart cart;

    @SneakyThrows
    @GetMapping("catalog")
    public String showCatalog(@RequestParam(required = false) String category,
                              @RequestParam(required = false) String subcategory,
                              Model model) {
        List<Crumb> crumbs = new LinkedList<>();

        if (category != null) {
            crumbs.add(HOME);
            crumbs.add(new Crumb(category, "catalog?category=" + category));

            if (subcategory != null) {
                crumbs.add(new Crumb(subcategory, "catalog?category=" + category + "&subcategory=" + subcategory));
            }
        } else {
            crumbs.add(CATALOG);
        }
        grouping.setCategory(category);
        grouping.setSubcategory(subcategory);

        model.addAttribute("crumbs", crumbs);

        var structure = productService.getProductStructure();
        model.addAttribute("structure", structure);

        var categoryInitialData = productService.getCategoryInitialData();
        model.addAttribute("sizes", categoryInitialData.getSizes());
        model.addAttribute("colors", categoryInitialData.getColors());
        model.addAttribute("priceMin", categoryInitialData.getPriceMin());
        model.addAttribute("priceMax", categoryInitialData.getPriceMax());

        model.addAttribute("recentProducts", recentViewed.getProducts());

        var products = productService.getAllProducts();
        //TODO quick workaround, filter in db query
        if (category != null) {
            products = products.stream()
                    .filter(product -> product.getGrouping()
                            .getCategory()
                            .getTitle()
                            .equals(category))
                    .collect(Collectors.toList());
        }

        if (subcategory != null) {
            products = products.stream()
                    .filter(product -> product.getGrouping()
                            .getSubcategory()
                            .getTitle()
                            .equals(subcategory))
                    .collect(Collectors.toList());
        }

        model.addAttribute("products", products);

        Cart cartRes = (Cart) ((Advised) cart).getTargetSource().getTarget();
        model.addAttribute("cart", cartRes);

        return "catalog";
    }

    @PostMapping("catalog")
    public String getCriteriaSelection(@RequestBody CategoryFilterRequest filter, Model model) {
        var products = productService.getCategoryResponse(filter);

        //TODO quick workaround, filter in db query
        if (grouping.getCategory() != null) {
            products = products.stream()
                    .filter(product -> product.getGrouping()
                            .getCategory()
                            .getTitle()
                            .equals(grouping.getCategory()))
                    .collect(Collectors.toList());
        }

        if (grouping.getSubcategory() != null) {
            products = products.stream()
                    .filter(product -> product.getGrouping()
                            .getSubcategory()
                            .getTitle()
                            .equals(grouping.getSubcategory()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("products", products);

        return "fragments/cardFragment :: cards";
    }

    @GetMapping("about")
    public String showAbout() {
        return "placeholder/about";
    }

    @GetMapping("careers")
    public String showCareers() {
        return "placeholder/careers";
    }

    @GetMapping("help")
    public String showHelp() {
        return "placeholder/help";
    }

    @GetMapping("faq")
    public String showFaq() {
        return "placeholder/faq";
    }

    @Getter
    @AllArgsConstructor
    public static class Crumb {
        private String name;
        private String link;
    }
}
