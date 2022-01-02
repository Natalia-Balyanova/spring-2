package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.dto.OrderDetailDto;
import com.gb.balyanova.spring2.entities.Order;
import com.gb.balyanova.spring2.entities.OrderItem;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.entities.User;
import com.gb.balyanova.spring2.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final CartService cartService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailDto orderDetailDto){
        String orderPhone = orderDetailDto.getPhone();
        String orderAddress = orderDetailDto.getAddress();
        int orderPrice = cartService.getCurrentCart().getTotalPrice();
        Order order = orderService.saveOrder(new Order(orderPhone,orderAddress,orderPrice));
        List<OrderItem> orderItems = cartService.getCurrentCart().getItems()
                .stream().map(orderItemDto -> new OrderItem (orderItemDto, order)).collect(Collectors.toList());
        System.out.println("*******************");
        for (OrderItem oi: orderItems) {
            orderItemService.saveOrderItem(oi);
        }
        cartService.clear();
        orderService.orderInfo(order.getId());
    }
}