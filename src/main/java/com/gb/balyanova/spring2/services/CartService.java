package com.gb.balyanova.spring2.services;

import com.gb.balyanova.spring2.dto.Cart;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addProductByIdToCart(Long productId) {
        if (!getCurrentCart().addProduct(productId)) {
            Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
            getCurrentCart().addProduct(product);
        }
    }

    public void clear() {
        getCurrentCart().clear();
    }

    public void removeItem(Long productId) {
        getCurrentCart().removeProduct(productId);
    }

    public void decrease(Long productId) { getCurrentCart().decreaseProduct(productId);}
}

