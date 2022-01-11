package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.dto.Cart;
import com.gb.balyanova.spring2.dto.ProductDto;
import com.gb.balyanova.spring2.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
    }

    @GetMapping("/remove/{id}")
    public void removeItem (@PathVariable Long id){
        cartService.removeItem(id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.getCurrentCart().clear();
    }
}
