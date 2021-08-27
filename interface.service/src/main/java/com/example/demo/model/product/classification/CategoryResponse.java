package com.example.demo.model.product.classification;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryResponse {
    private List<String> categories;
}
