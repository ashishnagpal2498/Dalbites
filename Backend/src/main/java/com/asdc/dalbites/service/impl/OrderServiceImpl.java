package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.mappers.OrderMapper;
import com.asdc.dalbites.mappers.OrderItemMapper;
import com.asdc.dalbites.model.DAO.*;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.*;
import com.asdc.dalbites.service.EmailService;
import com.asdc.dalbites.service.OrderService;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.asdc.dalbites.util.Constants;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
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
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Value("${role.user}")
    private int userRole;

    @Value("${role.restaurant}")
    private int restaurantRole;

    @Override
    public List<OrderDTO> getAllOrders(Principal principal) throws ResourceNotFoundException {
        String username = principal.getName();
        LoginDao loginDao = loginRepository.findByUsername(username);

        if (loginDao.getRoleDao().getId() == userRole) {
            UserDao user = userRepository.findByLogin_Id(loginDao.getId());
            return getAllOrdersByUserId(user.getUserId());

        } else {
            RestaurantDao restaurantDao = restaurantRepository.findByLogin_Id(loginDao.getId());
            return getAllOrdersByRestaurantId(restaurantDao.getId());
        }
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser_UserId(userId)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findAllByRestaurant_Id(restaurantId)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrder(Long orderId) throws ResourceNotFoundException {
        Optional<OrderDao> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            return orderMapper.toOrderDTO(order.get());
        } else{
            throw new ResourceNotFoundException("Order not found on :: " + orderId);
        }
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

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, String token) throws ResourceNotFoundException {
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(Constants.TOKEN_START_INDEX));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());

        List<Optional<MenuItemDao>> menuItems = orderDTO.getOrderItems().stream()
                .map(orderItem -> menuItemRepository.findById(orderItem.getItem().getId()))
                .collect(Collectors.toList());

        UserDao user = userRepository.findByUserId(userId);
        OrderDao orderDao = orderMapper.toOrderDao(orderDTO);
        orderDao.setUser(user);
        Optional<RestaurantDao> restaurantDao = restaurantRepository.findById(orderDTO.getRestaurantId());
        if (restaurantDao.isPresent()) {
            orderDao.setRestaurant(restaurantDao.get());
        } else{
            throw new ResourceNotFoundException("Order not found on :: " + orderDTO.getRestaurantId());
        }

        for (OrderItemDao orderItemDao : orderDao.getOrderItems()) {
            MenuItemDao menuItem = menuItemRepository.findById(orderItemDao.getItem().getId()).get();
            orderItemDao.setItem(menuItem);
            orderItemDao.setOrder(orderDao);
        }

        orderRepository.save(orderDao);
        OrderDTO result = orderMapper.toOrderDTO(orderDao);
        emailService.sendOrderConfirmationEmail(result, menuItems, (String) tokenClaims.get("email"));
        return result;
    }

    private void sendReadyToPickupEmail(String userEmail) {
        String subject = "Your Order is Ready for Pickup";
        String text = Constants.ORDER_READY_BODY;
        emailService.sendEmail(userEmail, subject, text);
    }
}
