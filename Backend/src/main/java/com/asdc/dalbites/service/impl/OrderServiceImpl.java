package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.mappers.OrderMapper;
import com.asdc.dalbites.mappers.OrderItemMapper;
import com.asdc.dalbites.model.DAO.LoginDao;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.EmailService;
import com.asdc.dalbites.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Value("${role.user}")
    private int userRole;

    @Value("${role.restaurant}")
    private int restaurantRole;

    @Override
    public List<OrderDTO> getAllOrders(Principal principal) throws ResourceNotFoundException {
        String username = principal.getName();
        LoginDao loginDao = loginRepository.findByUsername(username);

        if (loginDao.getRoleDao().getId() == userRole) {
            return getAllOrdersByUserId(loginDao.getId());
        } else {
            RestaurantDao restaurantDao = restaurantRepository.findByLogin_Id(loginDao.getId());
            return getAllOrdersByRestaurantId(restaurantDao.getId());
        }
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser_UserId(userId)
                .stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findAllByRestaurant_Id(restaurantId)
                .stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrder(Long orderId) throws ResourceNotFoundException {
        OrderDao order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found on :: " + orderId));
        return OrderMapper.toOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatusDTO orderStatusDTO) throws ResourceNotFoundException {
        OrderDTO order = getOrder(orderId);
        OrderStatusEnum status = orderStatusDTO.getStatus();
        OrderStatusEnum oldStatus = order.getStatus();
        order.setStatus(status);

        orderRepository.save(orderMapper.toOrderDao(order));

        if (status == OrderStatusEnum.READY_TO_PICKUP && oldStatus != OrderStatusEnum.READY_TO_PICKUP) {
            sendReadyToPickupEmail(orderMapper.toOrderDao(order).getUser().getEmail());
        }
        return order;
    }

    private void sendReadyToPickupEmail(String userEmail) {
        String subject = "Your Order is Ready for Pickup";
        String text = "Dear Customer,\n\nYour order is now ready for pickup. Please visit the restaurant to collect your order.";
        emailService.sendEmail(userEmail, subject, text);
    }
}
