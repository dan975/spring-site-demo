package com.example.demo.model.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ColorMapping {
    private int colorId;
    private String name;
    private String rgb;
}
