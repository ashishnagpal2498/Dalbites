package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.mappers.OrderMapper;
import com.asdc.dalbites.model.DAO.*;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import com.asdc.dalbites.repository.LoginRepository;
import com.asdc.dalbites.repository.OrderRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.repository.UserRepository;
import com.asdc.dalbites.service.EmailService;
import com.asdc.dalbites.service.impl.OrderServiceImpl;
import com.asdc.dalbites.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private JwtTokenUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_RestaurantRole_Success() throws ResourceNotFoundException {
        String username = "restaurant";
        Principal principal = createPrincipal(username, 2);
        LoginDao loginDao = new LoginDao();
        RoleDao roleDao = new RoleDao();
        roleDao.setId(2);
        loginDao.setRoleDao(roleDao);
        loginDao.setId(1L);
        RestaurantDao restaurantDao = new RestaurantDao();
        restaurantDao.setId(1L);
        when(loginRepository.findByUsername(username)).thenReturn(loginDao);
        when(restaurantRepository.findByLogin_Id(loginDao.getId())).thenReturn(restaurantDao);
        when(orderRepository.findAllByRestaurant_Id(restaurantDao.getId())).thenReturn(Collections.emptyList());
        List<OrderDTO> result = orderService.getAllOrders(principal);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository, never()).findAllByUser_UserId(anyLong());
        verify(orderRepository, times(1)).findAllByRestaurant_Id(anyLong());
    }

    @Test
    void getAllOrdersByUserId_Success() {
        Long userId = 1L;
        when(orderRepository.findAllByUser_UserId(userId)).thenReturn(Collections.emptyList());
        List<OrderDTO> result = orderService.getAllOrdersByUserId(userId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void getAllOrdersByRestaurantId_Success() {
        Long restaurantId = 1L;
        when(orderRepository.findAllByRestaurant_Id(restaurantId)).thenReturn(Collections.emptyList());
        List<OrderDTO> result = orderService.getAllOrdersByRestaurantId(restaurantId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getOrder_Success() throws ResourceNotFoundException {
        Long orderId = 1L;
        OrderDao mockOrder = new OrderDao();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        OrderDTO mockOrderDTO = new OrderDTO();
        mockOrderDTO.setOrderItems(new ArrayList<>());
        when(orderMapper.toOrderDTO(mockOrder)).thenReturn(mockOrderDTO);
        OrderDTO result = orderService.getOrder(orderId);
        assertNotNull(result);
        assertEquals(mockOrder.getOrderId(), result.getOrderId());
    }

    @Test
    void getOrder_ResourceNotFoundException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(orderId));
    }

    @Test
    void testCreateOrder_Success() {
        OrderDTO order = orderService.createOrder(null,"abc");
        assertNull(order);
    }

    private Principal createPrincipal(String username, int roleId) {
        return new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
    }

}
