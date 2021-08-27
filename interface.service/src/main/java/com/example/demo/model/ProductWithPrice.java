package com.example.demo.model;

import com.example.demo.model.price.ProductPrice;
import com.example.demo.model.product.Product;
import com.example.demo.model.product.ProductImage;
import com.example.demo.model.product.grouping.Grouping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ProductWithPrice {
    private Long id;
    private String productName;
    private String productDescription;
    private Grouping grouping;
    private List<ProductImage> images;
    private Double price;

    public ProductWithPrice(Product product, ProductPrice price) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.grouping = product.getGrouping();
        this.images = product.getImages();
        this.price = price.getPrice();
    }

    public ProductImage getMainImage() {
        return images.stream()
                .filter(ProductImage::isMainImage)
                .findAny()
                .get();
    }
}
