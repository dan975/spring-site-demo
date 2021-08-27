package com.example.demo.model.stock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StockDetails {
    private List<String[]> colors;
    private List<String> sizes;
}
