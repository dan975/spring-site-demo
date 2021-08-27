package com.example.demo.services.micro;

import com.example.demo.model.price.PriceRangeResponse;
import com.example.demo.model.price.ProductPriceResponse;
import com.example.demo.model.price.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PriceMicroService {
    private static final String BASE = "http://price-service/product-prices";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ProductPriceResponse getAllProductPrices() {
        return webClientBuilder.build()
                .get()
                .uri(BASE)
                .retrieve()
                .bodyToMono(ProductPriceResponse.class)
                .block();
    }

    public ProductPriceResponse getAllProductPrices(Double minPrice, Double maxPrice) {
        return webClientBuilder.build()
                .get()
                .uri(String.format("%s?minPrice=%f&maxPrice=%f", BASE, minPrice, maxPrice))
                .retrieve()
                .bodyToMono(ProductPriceResponse.class)
                .block();
    }

    public ProductPrice getProductPriceById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri(String.format("%s/%d", BASE, id))
                .retrieve()
                .bodyToMono(ProductPrice.class)
                .block();
    }

    public PriceRangeResponse getPriceRange() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/price-range")
                .retrieve()
                .bodyToMono(PriceRangeResponse.class)
                .block();
    }
}
