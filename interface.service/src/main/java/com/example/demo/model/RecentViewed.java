package com.example.demo.model;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecentViewed {
    private static final int LIMIT = 2;
    private final Queue<ProductWithPrice> products = new LinkedList<>();

    public void add(ProductWithPrice product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
        products.add(product);

        if (products.size() > LIMIT) {
            products.poll();
        }
    }
}
