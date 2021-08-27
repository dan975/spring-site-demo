package com.example.demo.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class CartItem {
    private Long id;
    private CartImage image;
    private int count;
    private String color;
    private String size;
    private double price;
    private String name;

}
