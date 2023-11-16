package com.asdc.dalbites.Controller;

import com.asdc.dalbites.controller.OrderController;
import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderStatusDTO;
import com.asdc.dalbites.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void getAllOrderOfCurrentUser_ResourceNotFoundException() throws ResourceNotFoundException {
        Principal principal = createPrincipal("user");
        when(orderService.getAllOrders(principal)).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrderOfCurrentUser(principal);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService, times(1)).getAllOrders(principal);
    }

    @Test
    void getOrder_Success() throws ResourceNotFoundException {
        Long orderId = 1L;
        OrderDTO mockOrder = new OrderDTO();
        when(orderService.getOrder(orderId)).thenReturn(mockOrder);
        ResponseEntity<OrderDTO> response = orderController.getOrder(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(mockOrder, response.getBody());
        verify(orderService, times(1)).getOrder(orderId);
    }

    @Test
    void updateOrderStatus_Success() throws ResourceNotFoundException {
        Long orderId = 1L;
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        OrderDTO mockUpdatedOrder = new OrderDTO();
        when(orderService.updateOrderStatus(orderId, orderStatusDTO)).thenReturn(mockUpdatedOrder);
        ResponseEntity<OrderDTO> response = orderController.updateOrderStatus(orderId, orderStatusDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(mockUpdatedOrder, response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, orderStatusDTO);
    }

    private Principal createPrincipal(String username) {
        return new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
    }
}


