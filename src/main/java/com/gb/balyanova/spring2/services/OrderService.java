package com.gb.balyanova.spring2.services;

import com.gb.balyanova.spring2.dto.Cart;
import com.gb.balyanova.spring2.dto.OrderDetailsDto;
import com.gb.balyanova.spring2.entities.Order;
import com.gb.balyanova.spring2.entities.OrderItem;
import com.gb.balyanova.spring2.entities.Product;
import com.gb.balyanova.spring2.entities.User;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import com.gb.balyanova.spring2.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public void createOrder(User user, OrderDetailsDto orderDetailsDto) {
        Cart currentCart = cartService.getCurrentCart();
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(order.getPhone());
        order.setUser(user);
        order.setTotalPrice(currentCart.getTotalPrice());

        List<OrderItem> items = currentCart.getItems().stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(orderItemDto.getQuantity());
                    item.setPricePerProduct(orderItemDto.getPricePerProduct());
                    item.setPrice(orderItemDto.getPrice());
                    item.setProduct(productService.findById(orderItemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        currentCart.clear();
    }

    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }
}