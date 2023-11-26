package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrderOfCurrentUser(Principal principal) {
        try {
            List<OrderDTO> orders = orderService.getAllOrders(principal);
            return ResponseEntity.ok(orders);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, orderStatusDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("Authorization") String bearerToken, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO, bearerToken);
            return ResponseEntity.ok(createdOrder);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
