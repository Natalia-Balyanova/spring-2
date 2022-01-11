package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.converter.OrderConverter;
import com.gb.balyanova.spring2.dto.OrderDetailsDto;
import com.gb.balyanova.spring2.dto.OrderDto;
import com.gb.balyanova.spring2.entities.Order;
import com.gb.balyanova.spring2.entities.OrderItem;
import com.gb.balyanova.spring2.entities.User;
import com.gb.balyanova.spring2.exceptions.ResourceNotFoundException;
import com.gb.balyanova.spring2.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailDto){
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal){
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}