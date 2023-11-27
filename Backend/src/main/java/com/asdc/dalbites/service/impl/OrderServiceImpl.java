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
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link OrderService} interface providing methods for managing orders.
 */
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

    /**
     * Retrieves all orders for the user or restaurant associated with the provided principal.
     *
     * @param principal The principal representing the authenticated user or restaurant.
     * @return A list of {@link OrderDTO} representing the orders.
     * @throws ResourceNotFoundException If the user or restaurant is not found.
     */
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

    /**
     * Retrieves all orders associated with a specific user.
     *
     * @param userId The ID of the user for whom orders are retrieved.
     * @return A list of {@link OrderDTO} representing the orders for the user.
     */
    @Override
    public List<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser_UserId(userId)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all orders associated with a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant for which orders are retrieved.
     * @return A list of {@link OrderDTO} representing the orders for the restaurant.
     */
    @Override
    public List<OrderDTO> getAllOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findAllByRestaurant_Id(restaurantId)
                .stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return A {@link OrderDTO} representing the retrieved order.
     * @throws ResourceNotFoundException If the order is not found.
     */
    @Override
    public OrderDTO getOrder(Long orderId) throws ResourceNotFoundException {
        Optional<OrderDao> order = orderRepository.findById(orderId);
        if(order.isPresent()){
            return orderMapper.toOrderDTO(order.get());
        } else{
            throw new ResourceNotFoundException("Order not found on :: " + orderId);
        }
    }


    /**
     * Updates the status of a specific order.
     *
     * @param orderId         The ID of the order to update.
     * @param orderStatusDTO  The data transfer object containing the new order status.
     * @return A {@link OrderDTO} representing the updated order.
     * @throws ResourceNotFoundException If the order is not found.
     */
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
        Claims tokenClaims = jwtUtil.getAllClaimsFromToken(token.substring(7));
        Long userId = Long.parseLong(tokenClaims.get("user_id").toString());

        List<Optional<MenuItemDao>> menuItems = orderDTO.getOrderItems().stream()
                .map(orderItem -> menuItemRepository.findById(orderItem.getItem().getId()))
                .collect(Collectors.toList());

        UserDao user = userRepository.findByUserId(userId);
        OrderDao orderDao = orderMapper.toOrderDao(orderDTO);
        orderDao.setUser(user);
        RestaurantDao restaurantDao = restaurantRepository.findById(orderDTO.getRestaurantId()).orElseThrow(() -> new ResourceNotFoundException("Order not found on :: " + orderDTO.getRestaurantId()));
        orderDao.setRestaurant(restaurantDao);

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

    /**
     * Sends an email notification to the user when the order is ready for pickup.
     *
     * @param userEmail The email address of the user to notify.
     */
    private void sendReadyToPickupEmail(String userEmail) {
        String subject = "Your Order is Ready for Pickup";
        String text = "Dear Customer,\n\nYour order is now ready for pickup. Please visit the restaurant to collect your order.";
        emailService.sendEmail(userEmail, subject, text);
    }
}
