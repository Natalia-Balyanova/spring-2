package com.gb.balyanova.spring2.entities;

import com.gb.balyanova.spring2.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_per_product")
    private int pricePerProduct;

    @Column(name = "price")
    private int price;

//    @JoinColumn(name = "user_id")
//    private User userId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(OrderItemDto orderItemDto, Order order) {
        this.productId = orderItemDto.getProductId();
        this.quantity = orderItemDto.getQuantity();
        this.pricePerProduct = orderItemDto.getPricePerProduct();
        this.price = orderItemDto.getPrice();
        this.order = order;
//        this.userId = userId;
    }
}