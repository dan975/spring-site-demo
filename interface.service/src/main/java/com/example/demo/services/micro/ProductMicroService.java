package com.example.demo.services.micro;

import com.example.demo.model.StructureResponse;
import com.example.demo.model.product.Product;
import com.example.demo.model.product.ProductResponse;
import com.example.demo.model.product.classification.CategoryResponse;
import com.example.demo.model.product.classification.ClassificationResponse;
import com.example.demo.model.product.classification.SubcategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@Service
public class ProductMicroService {
    private static final String BASE = "http://product-service";

    @Autowired
    private WebClient.Builder webClientBuilder;

    public ProductResponse getProducts() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/products")
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }

    public ProductResponse getProducts(List<Long> productKeys) {
        var uri = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/products")
                .queryParam("ids", productKeys)
                .build();

        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }

    public Product getById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri(String.format("%s/products/%d", BASE, id))
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public CategoryResponse getAllCategories() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/categories")
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .block();
    }

    public SubcategoryResponse getAllSubcategories() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/subcategories")
                .retrieve()
                .bodyToMono(SubcategoryResponse.class)
                .block();
    }

    public ProductResponse getProductsContainingName(String name) {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/products/filter?name=" + name)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }

    public ClassificationResponse getClassification(Long categoryId, Long subcategoryId) {
        var uriBuilder = new DefaultUriBuilderFactory(BASE)
                .builder()
                .path("/classification")
                .queryParam("categoryId", categoryId);
        if (subcategoryId != null) {
            uriBuilder = uriBuilder.queryParam("subcategoryId", subcategoryId);
        }

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder.build())
                .retrieve()
                .bodyToMono(ClassificationResponse.class)
                .block();
    }

    public StructureResponse getStructure() {
        return webClientBuilder.build()
                .get()
                .uri(BASE + "/products/structure")
                .retrieve()
                .bodyToMono(StructureResponse.class)
                .block();
    }
}
