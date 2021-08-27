package com.example.demo.model.product.classification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassificationResponse {
    private String category;
    private String subcategory;
}
