package com.example.demo.model.product;

import com.example.demo.model.product.grouping.Grouping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private Long id;
    private String productName;
    private String productDescription;
    private Grouping grouping;
    private List<ProductImage> images;

    public ProductImage getMainImage() {
        return images.stream()
                .filter(ProductImage::isMainImage)
                .findAny()
                .get();
    }
}
