package com.gb.balyanova.spring2.dto;

//import com.gb.balyanova.spring2.entities.Category;
import com.gb.balyanova.spring2.entities.Category;
import com.gb.balyanova.spring2.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer price;
    private String categoryTitle;

    public ProductDto (Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
    }
}
