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
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public LoginRepository loginRepository;

    @Autowired
    public RestaurantRepository restaurantRepository;

    @Value("${role.user}")
    private int userRole;

    @Value("${role.restaurant}")
    private int restaurantRole;

    @GetMapping()
    public ResponseEntity<List<OrderDao>> getAllOrderOfCurrentUser(Principal principal){
        String username = principal.getName();
        LoginDao loginDao = loginRepository.findByUsername(username);
        if (loginDao.getRoleDao().getId() == userRole) {
            List<OrderDao> userOrders = orderService.getAllOrdersByUserId(loginDao.getId());
            return ResponseEntity.ok(userOrders);
        }
        else {
            RestaurantDao restaurantDao = restaurantRepository.findByLogin_Id(loginDao.getId());  ;
            List<OrderDao> restaurantOrders = orderService.getAllOrdersByRestaurantId(restaurantDao.getId());
            return ResponseEntity.ok(restaurantOrders);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDao> getOrder(@PathVariable Long orderId) {
        try {
            OrderDao order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDao> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
        try {
            OrderStatusEnum status = orderStatusDTO.getStatus();
            OrderDao order = orderService.getOrder(orderId);
            order.setStatus(status);
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
