package com.example.demo.model.product.grouping;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Grouping {
    private Long productKey;
    private Category category;
    private Subcategory subcategory;
}
