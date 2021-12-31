package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.dto.ProductDto;
import com.gb.balyanova.spring2.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public void addProductInCard (@PathVariable Long id){
        cartService.addProductInCart(id);
    }

    @GetMapping("/cartInfo")
    public List<ProductDto> sendCardInfo (){
        return cartService.cardInfo();
    }
    @DeleteMapping("/cartInfo/{id}") public void deleteById(@PathVariable Long id) { cartService.deleteByIdFromCartId(id); }
}
