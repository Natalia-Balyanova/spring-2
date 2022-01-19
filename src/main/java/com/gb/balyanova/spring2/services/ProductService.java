package com.gb.balyanova.spring2.services;

import com.gb.balyanova.spring2.dto.ProductDto;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import com.gb.balyanova.spring2.repositories.ProductRepository;
import com.gb.balyanova.spring2.specifications.ProductSpecification;
import com.gb.balyanova.spring2.ws.products.ProductSoap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, Integer page, String categoryPart) {
        Specification<Product> spec = Specification.where(null);
        //select p from Product p where true
        if (minPrice != null){
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(minPrice));
        }
        //select p from Product p where true AND p.price < maxPrice
        if (maxPrice != null){
            spec = spec.and(ProductSpecification.priceLessOrEqualsThan(maxPrice));
        }
        if (partTitle != null){
            spec = spec.and(ProductSpecification.titleLike(partTitle));
        }
        if (categoryPart != null){
            spec = spec.and(ProductSpecification.categoryEqual(categoryPart));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5)); //+sortBy
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

//    @Transactional
//    public void changePrice(Long productId, Integer delta) {
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Unable to change product`s price, id: " + productId));
//        product.setPrice(product.getPrice() + delta);
//    }

    @Transactional
    public Product updateProductFromDto(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Product Not Found in DB, id: " + productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public List<Product> findByPriceBetween(Integer min, Integer max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public List<Product> findLowPriceProducts(Integer min) {
        return productRepository.findLowPriceProducts(min);
    }

    public List<Product> findMoreThanValue(Integer max) { return productRepository.findMoreThanValue(max);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public static final Function<Product, ProductSoap> functionEntityToSoap = pe -> {
        ProductSoap p = new ProductSoap();
        p.setId(pe.getId());
        p.setTitle(pe.getTitle());
        p.setPrice(pe.getPrice());
        p.setCategoryTitle(pe.getCategory().getTitle());
        return p;
    };

    public List<ProductSoap> findAllSoap() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public ProductSoap findByIdSoap(Long id) {
        return productRepository.findByIdQuery(id).map(functionEntityToSoap).get();
    }
}