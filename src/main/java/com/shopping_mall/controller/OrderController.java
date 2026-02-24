package com.shopping_mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping_mall.dto.OrderDTO;
import com.shopping_mall.entity.OrderItem;
import com.shopping_mall.service.OrderService;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public OrderDTO addOrder(@RequestBody List<OrderItem> items) {
        return orderService.addOrder(items);
    }

    @PutMapping("/update/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody List<OrderItem> items) {
        return orderService.updateOrder(id, items);
    }

    @GetMapping("/search/{id}")
    public OrderDTO searchOrder(@PathVariable Long id) {
        return orderService.searchOrderById(id);
    }

    @PutMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @PutMapping("/add-items/{id}")
    public OrderDTO addItems(@PathVariable Long id, @RequestBody List<OrderItem> items) {
        return orderService.addItemsToOrder(id, items);
    }

    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
