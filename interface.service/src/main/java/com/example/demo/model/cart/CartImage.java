package com.example.demo.model.cart;

import com.example.demo.model.ImageBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartImage extends ImageBase {
    private final byte[] image;
}
