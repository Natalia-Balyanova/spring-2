package com.gb.balyanova.spring2.services;

import com.gb.balyanova.spring2.entities.Order;
import com.gb.balyanova.spring2.entities.OrderItem;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import com.gb.balyanova.spring2.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException
                ("Order id: " + orderId + " not found"));
    }

    @Transactional
    public void saveItems(Long orderId, List<OrderItem> orderItems) {
        Order order = findOrderById(orderId);
        order.setOrderItems(orderItems);
        log.info(order.toString());
    }

    @Transactional
    public void orderInfo (Long orderId){
        Order order = findOrderById(orderId);
        List<OrderItem> itemsInOrder = order.getOrderItems();
        log.info(String.format("Order Info: id=%d, phone=%s, address=%s, totalPrice=%d", order.getId(), order.getPhone(),order.getAddress(),order.getTotalPrice()));
        log.info("Products in order: " + itemsInOrder);
    }
}