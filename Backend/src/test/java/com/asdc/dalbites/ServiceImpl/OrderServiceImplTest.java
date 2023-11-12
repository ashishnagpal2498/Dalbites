package com.asdc.dalbites.ServiceImpl;

import com.asdc.dalbites.service.impl.OrderServiceImpl;
import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderServiceImpl orderService;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void getOrder() {
        Long orderId = 1L;
        OrderDao mockOrder = new OrderDao();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        try {
            OrderDao result = orderService.getOrder(orderId);
            assertNotNull(result);
            assertSame(mockOrder, result);
        } catch (ResourceNotFoundException e) {
            fail("ResourceNotFoundException not expected");
        }
    }

    @Test
    void getOrder_ResourceNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(orderId));
    }

    @Test
    void getAllOrdersByUserId() {
        Long userId = 1L;
        when(orderRepository.findAllByUser_UserId(userId)).thenReturn(Collections.emptyList());
        List<OrderDao> result = orderService.getAllOrdersByUserId(userId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllOrdersByRestaurantId() {
        Long restaurantId = 1L;
        when(orderRepository.findAllByRestaurant_Id(restaurantId)).thenReturn(Collections.emptyList());
        List<OrderDao> result = orderService.getAllOrdersByRestaurantId(restaurantId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
