package com.gb.balyanova.spring2.converter;


import com.gb.balyanova.spring2.dto.OrderItemDto;
import com.gb.balyanova.spring2.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
        //return new OrderItem(orderItemDto.getId(), orderItemDto.getTitle(), orderItemDto.getPrice());
    }

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
