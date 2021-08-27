package com.example.demo.model.stock.responses;

import com.example.demo.model.stock.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StockResponse {
    private List<Stock> stockList;
}
