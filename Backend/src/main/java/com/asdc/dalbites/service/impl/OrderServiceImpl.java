package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public OrderDao getOrder(Long orderId) throws ResourceNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found on :: " + orderId));
    }

    public List<OrderDao> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser_UserId(userId);
    }

    public List<OrderDao> getAllOrdersByRestaurantId(Long restaurantId){
        return orderRepository.findAllByRestaurant_Id(restaurantId);
    }
}
