package com.example.demo.model.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Stock {
    private Integer id;
    private Long productKey;
    private String serialNumber;
    private SizeMapping size;
    private ColorMapping name;
}
