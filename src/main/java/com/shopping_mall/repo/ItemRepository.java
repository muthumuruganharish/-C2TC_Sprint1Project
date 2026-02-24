package com.shopping_mall.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping_mall.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	
}