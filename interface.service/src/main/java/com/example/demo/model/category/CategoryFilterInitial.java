package com.example.demo.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFilterInitial {
    private List<String> sizes;
    private List<FilterColor> colors;
    private double priceMin;
    private double priceMax;

    @Getter
    @AllArgsConstructor
    public static class FilterColor {
        private String name;
        private String rgb;
        private boolean isTickBlack;
    }
}
