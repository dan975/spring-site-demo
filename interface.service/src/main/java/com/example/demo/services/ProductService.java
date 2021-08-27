package com.example.demo.services;

import com.example.demo.model.ProductWithPrice;
import com.example.demo.model.category.CategoryFilterInitial;
import com.example.demo.model.category.CategoryFilterRequest;
import com.example.demo.model.price.ProductPrice;
import com.example.demo.model.product.classification.ClassificationResponse;
import com.example.demo.model.stock.StockDetails;
import com.example.demo.services.micro.PriceMicroService;
import com.example.demo.services.micro.ProductMicroService;
import com.example.demo.services.micro.StockMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductService {

    @Autowired
    private ProductMicroService productMicroService;

    @Autowired
    private PriceMicroService priceMicroService;

    @Autowired
    private StockMicroservice stockMicroservice;

    public ProductWithPrice getById(Long id) {
        var product = productMicroService.getById(id);
        var price = priceMicroService.getProductPriceById(id);

        return new ProductWithPrice(product, price);
    }

    public List<ProductWithPrice> getAllProducts() {
        return productMicroService.getProducts()
                .getProducts()
                .stream()
                .map(product -> {
                    var price = priceMicroService.getProductPriceById(product.getId());
                    return new ProductWithPrice(product, price);
                })
                .collect(Collectors.toList());
    }

    public CategoryFilterInitial getCategoryInitialData() {
        StockDetails stockDetails = stockMicroservice.getStockDetails();
        var colors = stockDetails.getColors()
                .stream()
                .map(e -> new CategoryFilterInitial.FilterColor(e[0], e[1], isTickBlack(e[1])))
                .collect(Collectors.toList());

        var priceRange = priceMicroService.getPriceRange();
        return CategoryFilterInitial.builder()
                .sizes(stockDetails.getSizes())
                .colors(colors)
                .priceMin(priceRange.getMinPrice())
                .priceMax(priceRange.getMaxPrice())
                .build();
    }

    private static boolean isTickBlack(String rgb) {
        //From: https://stackoverflow.com/a/3943023
        int red = Integer.parseInt(rgb.substring(0, 2), 16);
        int green = Integer.parseInt(rgb.substring(2, 4), 16);
        int blue = Integer.parseInt(rgb.substring(4, 6), 16);

        return red * 0.299 + green * 0.587 + blue * 0.114 > 186;
    }

    public List<ProductWithPrice> getCategoryResponse(CategoryFilterRequest filter) {
        var prices = priceMicroService.getAllProductPrices(filter.getMinPrice(), filter.getMaxPrice());
        var priceIds = prices.getPrices()
                .stream()
                .map(ProductPrice::getProductKey)
                .collect(Collectors.toList());

        var colors = getFilter(filter.getColors());
        var sizes = getFilter(filter.getSizes());
        var stockIds = stockMicroservice.getStockFilterResponse(colors, sizes, filter.getIsInStock())
                .getProductKeys();

        var matchingProductKeys = priceIds.stream()
                .filter(stockIds::contains)
                .collect(Collectors.toList());
        var matchingProducts = productMicroService.getProducts(matchingProductKeys)
                .getProducts();

        return matchingProducts.stream()
                .map(product -> {
                    var price = prices.getPrices()
                            .stream()
                            .filter(e -> e.getProductKey().equals(product.getId()))
                            .findAny()
                            .orElseGet(() -> priceMicroService.getProductPriceById(product.getId()));

                    return new ProductWithPrice(product, price);
                })
                .collect(Collectors.toList());
    }

    private List<String> getFilter(String[][] selection) {
        var keys = Arrays.stream(selection)
                .filter(e -> Boolean.parseBoolean(e[1]))
                .map(e -> e[0])
                .collect(Collectors.toList());

        if (keys.isEmpty()) {
            for (String[] filter : selection) {
                keys.add(filter[0]);
            }
        }

        return keys;
    }

    public List<String> getAllCategories() {
        return productMicroService.getAllCategories().getCategories();
    }

    public List<String> getAllSubcategories() {
        return productMicroService.getAllSubcategories().getSubcategories();
    }

    public List<ProductWithPrice> getProductsContainingName(String name) {
        return productMicroService.getProductsContainingName(name)
                .getProducts()
                .stream()
                .map(product -> {
                    var productPrice = priceMicroService.getProductPriceById(product.getId());
                    return new ProductWithPrice(product, productPrice);
                })
                .collect(Collectors.toList());
    }

    public ClassificationResponse getClassification(Long categoryId, Long subcategoryId) {
        return productMicroService.getClassification(categoryId, subcategoryId);
    }

    public Map<String, List<String>> getProductStructure() {
        return productMicroService.getStructure().getCategorySubCategoryMap();
    }
}
