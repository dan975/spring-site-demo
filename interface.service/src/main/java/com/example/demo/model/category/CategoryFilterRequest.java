package com.example.demo.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CategoryFilterRequest {
    private String[][] sizes;
    private String[][] colors;
    private Boolean isInStock;
    private Double minPrice;
    private Double maxPrice;
}
