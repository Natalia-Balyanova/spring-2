package com.gb.balyanova.spring2.repositories;

import com.gb.balyanova.spring2.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}