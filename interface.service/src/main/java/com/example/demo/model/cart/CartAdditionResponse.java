package com.example.demo.model.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartAdditionResponse {
    private CartItem addedItem;
    private Cart cart;
}
