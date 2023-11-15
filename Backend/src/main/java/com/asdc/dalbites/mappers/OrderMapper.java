package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static OrderDTO toOrderDTO(OrderDao orderDao) {
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
        return orderDTO;
    }

    public static OrderDao toOrderDao(OrderDTO orderDTO){
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
