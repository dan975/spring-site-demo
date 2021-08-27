package com.example.demo.model.price;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductPriceResponse {
    private List<ProductPrice> prices;
}
