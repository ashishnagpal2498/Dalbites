package com.asdc.dalbites.service;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Service interface for order-related operations.
 */
@Service
public interface OrderService {
    /**
     * Retrieves all orders associated with the current user.
     *
     * @param principal The principal representing the current user.
     * @return A list of {@link OrderDTO} representing the user's orders.
     * @throws ResourceNotFoundException If no orders are found for the current user.
     */
    List<OrderDTO> getAllOrders(Principal principal) throws ResourceNotFoundException;

    /**
     * Retrieves all orders associated with a specific user ID.
     *
     * @param userId The ID of the user.
     * @return A list of {@link OrderDTO} representing the user's orders.
     */
    List<OrderDTO> getAllOrdersByUserId(Long userId);

    /**
     * Retrieves all orders associated with a specific restaurant ID.
     *
     * @param restaurantId The ID of the restaurant.
     * @return A list of {@link OrderDTO} representing the restaurant's orders.
     */
    List<OrderDTO> getAllOrdersByRestaurantId(Long restaurantId);

    /**
     * Retrieves details of a specific order.
     *
     * @param orderId The ID of the order.
     * @return An {@link OrderDTO} representing the details of the order.
     * @throws ResourceNotFoundException If the order with the specified ID is not found.
     */
    OrderDTO getOrder(Long orderId) throws ResourceNotFoundException;

    /**
     * Updates the status of a specific order.
     *
     * @param orderId         The ID of the order.
     * @param orderStatusDTO  The data transfer object containing the updated order status.
     * @return An {@link OrderDTO} representing the updated details of the order.
     * @throws ResourceNotFoundException If the order with the specified ID is not found.
     */
    OrderDTO updateOrderStatus(Long orderId, OrderStatusDTO orderStatusDTO) throws ResourceNotFoundException;
    OrderDTO createOrder(OrderDTO orderDTO, String token) throws ResourceNotFoundException;
}
