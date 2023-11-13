package com.asdc.dalbites.Controller;

import com.asdc.dalbites.controller.OrderController;
import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.LoginDao;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.RestaurantDao;
import com.asdc.dalbites.model.DAO.RoleDao;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrderOfCurrentUser() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("restaurant");
        LoginDao loginDao = mock(LoginDao.class);
        when(loginRepository.findByUsername("restaurant")).thenReturn(loginDao);
        RoleDao roleDao = mock(RoleDao.class);
        when(loginDao.getRoleDao()).thenReturn(roleDao);
        when(roleDao.getId()).thenReturn(2); // Assuming restaurant role is 2
        RestaurantDao restaurantDao = mock(RestaurantDao.class);
        when(restaurantRepository.findByLogin_Id(anyLong())).thenReturn(restaurantDao);
        when(restaurantDao.getId()).thenReturn(1L);
        when(orderService.getAllOrdersByRestaurantId(anyLong())).thenReturn(Arrays.asList(new OrderDao(), new OrderDao()));
        ResponseEntity<List<OrderDao>> response = orderController.getAllOrderOfCurrentUser(principal);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getOrder_ValidOrderId_ReturnsOrder() throws ResourceNotFoundException {
        Long orderId = 1L;
        when(orderService.getOrder(orderId)).thenReturn(new OrderDao());
        ResponseEntity<OrderDao> response = orderController.getOrder(orderId);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getOrder_InvalidOrderId_ReturnsNotFound() throws ResourceNotFoundException {
        Long orderId = 1L;
        when(orderService.getOrder(orderId)).thenThrow(new ResourceNotFoundException("Order not found on :: " + orderId));
        ResponseEntity<OrderDao> response = orderController.getOrder(orderId);
        assertEquals(404, response.getStatusCodeValue());
    }

//    @Test
//    void updateOrderStatus_ValidOrderId_ReturnsUpdatedOrder() throws ResourceNotFoundException {
//        Long orderId = 1L;
//        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
//        orderStatusDTO.setStatus(OrderStatusEnum.PREPARING);
//        OrderDao orderDao = new OrderDao();
//        when(orderService.getOrder(orderId)).thenReturn(orderDao);
//        when(orderRepository.save(orderDao)).thenReturn(orderDao);
//        ResponseEntity<OrderDao> response = orderController.updateOrderStatus(orderId, orderStatusDTO);
//        assertNotNull(response.getBody());
//        assertEquals(OrderStatusEnum.PREPARING, response.getBody().getStatus());
//        assertEquals(200, response.getStatusCodeValue());
//    }

    @Test
    void updateOrderStatus_InvalidOrderId_ReturnsNotFound() throws ResourceNotFoundException {
        Long orderId = 1L;
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setStatus(OrderStatusEnum.PREPARING);
        when(orderService.updateOrderStatus(orderId, orderStatusDTO)).thenReturn(ResponseEntity.notFound().build());
        ResponseEntity<OrderDao> response = orderController.updateOrderStatus(orderId, orderStatusDTO);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

}
