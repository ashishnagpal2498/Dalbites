package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.LoginDao;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.RestaurantsRepository;
import com.asdc.dalbites.service.OrderService;
import com.asdc.dalbites.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDao>> getAllOrderOfCurrentUser(Principal principal) {
        try {
            List<OrderDao> orders = orderService.getAllOrders(principal);
            return ResponseEntity.ok(orders);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDao> getOrder(@PathVariable Long orderId) {
        try {
            OrderDao order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDao> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
        try {
            OrderDao updatedOrder = orderService.updateOrderStatus(orderId, orderStatusDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
