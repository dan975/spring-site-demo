package com.example.demo.model.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    @Getter
    private List<CartItem> cartItems = new ArrayList<>();

    @Getter
    @Setter
    private double shippingCost;

    @Getter
    @Setter
    private double totalCost;

    @Getter
    private double totalWithShipping;

    @Getter
    @Value("${shippingCost}")
    private double shippingCostItem;

    @Getter
    private int totalCount;

    public Cart(List<CartItem> cartItems, double shippingCost, double totalCost, double shippingCostItem, int totalCount, double totalWithShipping) {
        this.cartItems = cartItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.shippingCostItem = shippingCostItem;
        this.totalCount = totalCount;
        this.totalWithShipping = totalWithShipping;
    }

    public void add(CartItem cartItem) {
        cartItems.stream()
                .filter(e -> e.getId().equals(cartItem.getId()))
                .filter(e -> e.getSize().equals(cartItem.getSize()))
                .findAny()
                .ifPresentOrElse(
                        e -> e.setCount(e.getCount() + cartItem.getCount()),
                        () -> cartItems.add(cartItem));

        updateTotals();
    }

    public void remove(Long id) {
        cartItems.removeIf(e -> e.getId().equals(id));

        updateTotals();
    }

    private void updateTotals() {
        this.shippingCost = cartItems.stream()
                .map(CartItem::getCount)
                .reduce(0, Integer::sum)
                * shippingCostItem;
        this.totalCost = cartItems.stream()
                .map(c -> c.getPrice() * c.getCount())
                .reduce(0.0, Double::sum);
        this.totalCount = cartItems.stream()
                .map(CartItem::getCount)
                .reduce(0, Integer::sum);

        this.totalWithShipping = totalCost + shippingCost;
    }
}
