package com.gb.balyanova.spring2.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "total_price")
    private int totalPrice;

//    @JoinColumn(name = "user_id")
//    private User userId;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(String phone, String address, int totalPrice, List<OrderItem> orderItems) {
        this.phone = phone;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public Order(String phone, String address, int totalPrice) {
        this.phone = phone;
        this.address = address;
        this.totalPrice = totalPrice;
    }
}
