package com.shopping_mall.serviceimp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping_mall.dto.ItemDTO;
import com.shopping_mall.dto.OrderDTO;
import com.shopping_mall.dto.OrderItemDTO;
import com.shopping_mall.entity.Item;
import com.shopping_mall.entity.OrderDetails;
import com.shopping_mall.entity.OrderItem;
import com.shopping_mall.repo.ItemRepository;
import com.shopping_mall.repo.OrderRepository;
import com.shopping_mall.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Override
    public OrderDTO addOrder(List<OrderItem> items) {

        OrderDetails order = new OrderDetails();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        double total = 0;

        for (OrderItem oi : items) {
            Item item = itemRepo.findById(oi.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            oi.setItem(item);
            oi.setOrder(order);

            double subtotal = item.getPrice() * oi.getQuantity();
            oi.setSubtotal(subtotal);

            total += subtotal;
        }

        order.setTotalAmount(total);
        order.setOrderItems(items);

        OrderDetails savedOrder = orderRepo.save(order);

        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, List<OrderItem> items) {

        OrderDetails order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.getOrderItems().clear();

        double total = 0;

        for (OrderItem oi : items) {
            Item item = itemRepo.findById(oi.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            oi.setItem(item);
            oi.setOrder(order);

            double subtotal = item.getPrice() * oi.getQuantity();
            oi.setSubtotal(subtotal);

            total += subtotal;
        }

        order.setOrderItems(items);
        order.setTotalAmount(total);

        return convertToDTO(orderRepo.save(order));
    }

    @Override
    public OrderDTO searchOrderById(Long id) {
        return convertToDTO(orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found")));
    }

    @Override
    public String cancelOrder(Long id) {
        OrderDetails order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("CANCELLED");
        orderRepo.save(order);

        return "Order Cancelled Successfully";
    }

    @Override
    public OrderDTO addItemsToOrder(Long orderId, List<OrderItem> items) {

        OrderDetails order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        double total = order.getTotalAmount();

        for (OrderItem oi : items) {
            Item item = itemRepo.findById(oi.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            oi.setItem(item);
            oi.setOrder(order);

            double subtotal = item.getPrice() * oi.getQuantity();
            oi.setSubtotal(subtotal);

            total += subtotal;

            order.getOrderItems().add(oi);
        }

        order.setTotalAmount(total);

        return convertToDTO(orderRepo.save(order));
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    // ✅ ENTITY → DTO CONVERTER
    private OrderDTO convertToDTO(OrderDetails order) {

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> itemDTOs = new ArrayList<>();

        for (OrderItem oi : order.getOrderItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(oi.getId());
            itemDTO.setQuantity(oi.getQuantity());
            itemDTO.setSubtotal(oi.getSubtotal());

            ItemDTO item = new ItemDTO();
            item.setId(oi.getItem().getId());
            item.setName(oi.getItem().getName());
            item.setPrice(oi.getItem().getPrice());
            item.setCategory(oi.getItem().getCategory());

            itemDTO.setItem(item);
            itemDTOs.add(itemDTO);
        }

        dto.setOrderItems(itemDTOs);
        return dto;
    }
}
