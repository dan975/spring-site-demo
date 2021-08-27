package com.example.demo.model.price;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceRangeResponse {
    private Double minPrice;
    private Double maxPrice;
}
