package com.asdc.dalbites.mapper;


import com.asdc.dalbites.mappers.OrderMapper;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.UserDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    void toOrderDTO() {
        OrderDao orderDao = createSampleOrderDao();
        OrderDTO orderDTO = orderMapper.toOrderDTO(orderDao);
        assertEquals(orderDao.getOrderId(), orderDTO.getOrderId());
        assertEquals(orderDao.getTotalAmount(), orderDTO.getTotalAmount());
        assertEquals(orderDao.getStatus(), orderDTO.getStatus());
        assertEquals(orderDao.getSpecialInstruction(), orderDTO.getSpecialInstruction());
        assertEquals(orderDao.getCreatedAt(), orderDTO.getCreatedAt());
        assertEquals(orderDao.getUser().getUserId(), orderDTO.getUserId());
        assertEquals(orderDao.getRestaurant().getId(), orderDTO.getRestaurantId());
        assertEquals(orderDao.getRestaurant().getName(), orderDTO.getRestaurantName());
        assertEquals(orderDao.getRestaurant().getRestaurantImage(), orderDTO.getRestaurantImage());
    }

    private OrderDao createSampleOrderDao() {
        OrderDao orderDao = new OrderDao();
        orderDao.setOrderId(1L);
        orderDao.setTotalAmount(50.0);
        orderDao.setSpecialInstruction("No onions");
        orderDao.setCreatedAt(new Date());
        UserDao user = new UserDao();
        user.setUserId(101L);
        orderDao.setUser(user);
        RestaurantDao restaurant = new RestaurantDao();
        restaurant.setId(201L);
        restaurant.setName("Sample Restaurant");
        restaurant.setRestaurantImage("restaurant-image.jpg");
        orderDao.setRestaurant(restaurant);
        return orderDao;
    }

    private OrderDTO createSampleOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setTotalAmount(50.0);
        orderDTO.setSpecialInstruction("No onions");
        orderDTO.setCreatedAt(new Date());
        orderDTO.setUserId(101L);
        orderDTO.setRestaurantId(201L);
        orderDTO.setRestaurantName("Sample Restaurant");
        orderDTO.setRestaurantImage("restaurant-image.jpg");
        return orderDTO;
    }
}