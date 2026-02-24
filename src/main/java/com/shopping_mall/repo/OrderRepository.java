package com.shopping_mall.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_mall.entity.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Long> {
	
	
}