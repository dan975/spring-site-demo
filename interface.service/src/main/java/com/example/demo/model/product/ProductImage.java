package com.example.demo.model.product;

import com.example.demo.model.ImageBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductImage extends ImageBase {
    private Long id;
    private Long productKey;
    private byte[] image;
    private String color;
    private boolean isMainImage;
}
