package com.asdc.dalbites.service;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface OrderService {
    List<OrderDao> getAllOrders(Principal principal) throws ResourceNotFoundException;
    List<OrderDao> getAllOrdersByUserId(Long userId);
    List<OrderDao> getAllOrdersByRestaurantId(Long restaurantId);
    OrderDao getOrder(Long orderId) throws ResourceNotFoundException;
    OrderDao updateOrderStatus(Long orderId, OrderStatusDTO orderStatusDTO) throws ResourceNotFoundException;
}
