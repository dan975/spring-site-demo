package com.example.demo.model.stock.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StockFilterResponse {
    private List<Long> productKeys;
}
