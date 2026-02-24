package com.shopping_mall.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_mall.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
