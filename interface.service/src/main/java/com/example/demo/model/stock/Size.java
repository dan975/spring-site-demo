package com.example.demo.model.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Size {
    private Long id;
    private Long productKey;
    private SizeMapping size;
}
