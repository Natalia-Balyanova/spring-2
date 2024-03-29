package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.converter.ProductConverter;
import com.gb.balyanova.spring2.dto.ProductDto;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import com.gb.balyanova.spring2.services.ProductService;
import com.gb.balyanova.spring2.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if(page < 1) {
            page = 1;
        }
        return productService.findAll(minPrice, maxPrice, titlePart, page).map(
                p -> productConverter.entityToDto(p)
        );
    }

    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.updateProductFromDto(productDto);
        return productConverter.entityToDto(product);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productService. findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found, id: " + id));
        return productConverter.entityToDto(product);
//        if(product.isPresent()) {
//            return new ResponseEntity<>(product.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Product Not Found, id: " + id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}