package com.example.demo.services.micro;

import com.example.demo.model.stock.Stock;
import com.example.demo.model.stock.StockDetails;
import com.example.demo.model.stock.responses.ColorResponse;
import com.example.demo.model.stock.responses.SizeResponse;
import com.example.demo.model.stock.responses.StockFilterResponse;
import com.example.demo.model.stock.responses.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Service
public class StockMicroservice {
    private static final String BASE = "http://stock-service";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public StockResponse getAllStock() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/stock")
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();
    }

    public StockResponse getAllStock(List<Long> productKeys) {
        var uri = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/stock")
                .queryParam("productKeys", productKeys)
                .build();

        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();
    }

    public StockFilterResponse getStockFilterResponse(List<String> colors, List<String> sizes, Boolean isInStock) {
        var uri = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/stock/filter")
                .queryParam("colors", colors)
                .queryParam("sizes", sizes)
                .queryParam("isInStock", isInStock)
                .build();

        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(StockFilterResponse.class)
                .block();
    }

    public Stock getStockById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri(String.format("%s/stock/%d", BASE, id))
                .retrieve()
                .bodyToMono(Stock.class)
                .block();
    }

    public StockDetails getStockDetails() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/stock/details")
                .retrieve()
                .bodyToMono(StockDetails.class)
                .block();
    }

    public ColorResponse getColors(List<Long> ids) {
        var uri = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/colors")
                .queryParam("productKeys", ids)
                .build();

        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ColorResponse.class)
                .block();
    }

    public SizeResponse getSizes(List<Long> ids) {
        var uri = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/sizes")
                .queryParam("productKeys", ids)
                .build();

        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SizeResponse.class)
                .block();
    }
}
