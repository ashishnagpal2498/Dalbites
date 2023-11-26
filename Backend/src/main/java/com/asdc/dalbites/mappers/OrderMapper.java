package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


/**
 * Class responsible for mapping between OrderDao and OrderDTO.
 */
@Component
public class OrderMapper {

    /**
     * Converts OrderDao to OrderDTO.
     *
     * @param orderDao The OrderDao object to be converted.
     * @return The corresponding OrderDTO object.
     */
    public OrderDTO toOrderDTO(OrderDao orderDao) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderDao.getOrderId());
        orderDTO.setOrderItems(orderDao.getOrderItems().stream()
                .map(OrderItemMapper::toOrderItemDTO)
                .collect(Collectors.toList()));
        orderDTO.setTotalAmount(orderDao.getTotalAmount());
        orderDTO.setStatus(orderDao.getStatus());
        orderDTO.setSpecialInstruction(orderDao.getSpecialInstruction());
        orderDTO.setCreatedAt(orderDao.getCreatedAt());
        orderDTO.setUserId(orderDao.getUser().getUserId());
        orderDTO.setRestaurantId(orderDao.getRestaurant().getId());
        orderDTO.setRestaurantName(orderDao.getRestaurant().getName());
        orderDTO.setRestaurantImage(orderDao.getRestaurant().getRestaurantImage());
        return orderDTO;
    }

    /**
     * Converts OrderDTO to OrderDao.
     *
     * @param orderDTO The OrderDTO object to be converted.
     * @return The corresponding OrderDao object.
     */
    public OrderDao toOrderDao(OrderDTO orderDTO){
        OrderDao orderDao = new OrderDao();
        orderDao.setOrderId(orderDTO.getOrderId());
        orderDao.setOrderItems(orderDTO.getOrderItems().stream()
                .map(OrderItemMapper::toOrderItemDao)
                .collect(Collectors.toList()));
        orderDao.setTotalAmount(orderDTO.getTotalAmount());
        orderDao.setStatus(orderDTO.getStatus());
        orderDao.setSpecialInstruction(orderDTO.getSpecialInstruction());
        orderDao.setCreatedAt(orderDTO.getCreatedAt());

        UserDao user = new UserDao();
        user.setUserId(orderDTO.getUserId());
        orderDao.setUser(user);

        RestaurantDao restaurant = new RestaurantDao();
        restaurant.setId(orderDTO.getRestaurantId());
        orderDao.setRestaurant(restaurant);
        return orderDao;
    }
}
