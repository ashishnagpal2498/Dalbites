package com.asdc.dalbites.service;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface OrderService {
    List<OrderDTO> getAllOrders(Principal principal) throws ResourceNotFoundException;
    List<OrderDTO> getAllOrdersByUserId(Long userId);
    List<OrderDTO> getAllOrdersByRestaurantId(Long restaurantId);
    OrderDTO getOrder(Long orderId) throws ResourceNotFoundException;
    OrderDTO updateOrderStatus(Long orderId, OrderStatusDTO orderStatusDTO) throws ResourceNotFoundException;
    OrderDTO createOrder(OrderDTO orderDTO, String token) throws ResourceNotFoundException;
}
