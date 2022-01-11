package com.gb.balyanova.spring2.services;

import com.gb.balyanova.spring2.entities.OrderItem;
import com.gb.balyanova.spring2.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
