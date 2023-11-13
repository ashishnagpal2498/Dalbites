package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.service.EmailService;
import com.asdc.dalbites.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

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




    public ResponseEntity<OrderDao> updateOrderStatus(Long orderId, OrderStatusDTO orderStatusDTO) {
        try {
            OrderStatusEnum status = orderStatusDTO.getStatus();
            OrderDao order = getOrder(orderId);
            OrderStatusEnum oldStatus = order.getStatus();
            order.setStatus(status);
            orderRepository.save(order);
            if (status == OrderStatusEnum.READY_TO_PICKUP && oldStatus != OrderStatusEnum.READY_TO_PICKUP) {
                sendReadyToPickupEmail(order.getUser().getEmail());
            }
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    private void sendReadyToPickupEmail(String userEmail) {
        String subject = "Your Order is Ready for Pickup";
        String text = "Dear Customer,\n\nYour order is now ready for pickup. Please visit the restaurant to collect your order.";

        emailService.sendEmail(userEmail, subject, text);
    }
}
