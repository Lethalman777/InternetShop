package com.example.InternetShop.services;

import com.example.InternetShop.models.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface ShoppingCartService {
    void addProduct(Product product);
    void removeProduct(Product product);
    void clearProducts();
    Map<Product, Integer> productsInCart();
    BigDecimal totalPrice();
    void cartCheckout();
    void sendEmail(String to, String subject, String body);
}
