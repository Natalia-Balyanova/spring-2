package com.gb.balyanova.spring2.converter;

import com.gb.balyanova.spring2.dto.JwtRequest;
import com.gb.balyanova.spring2.dto.ProductDto;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
    public User jwtRequestToUser(JwtRequest jwtRequest) {
        return new User(jwtRequest.getUsername(), jwtRequest.getPassword());
    }
}
