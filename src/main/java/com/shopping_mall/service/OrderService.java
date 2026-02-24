package com.shopping_mall.service;

import java.util.List;

import com.shopping_mall.dto.OrderDTO;
import com.shopping_mall.entity.OrderItem;

public interface OrderService {

    OrderDTO addOrder(List<OrderItem> items);

    OrderDTO updateOrder(Long orderId, List<OrderItem> items);

    OrderDTO searchOrderById(Long id);

    String cancelOrder(Long id);

    OrderDTO addItemsToOrder(Long orderId, List<OrderItem> items);

    List<OrderDTO> getAllOrders();
}
