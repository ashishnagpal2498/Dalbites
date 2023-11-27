package com.asdc.dalbites.mapper;

import com.asdc.dalbites.mappers.OrderItemMapper;
import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.OrderItemDao;
import com.asdc.dalbites.model.DTO.OrderItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderItemMapperTest {

    @Test
    void toOrderItemDTO() {
        OrderItemDao orderItemDao = createSampleOrderItemDao();
        OrderItemDTO orderItemDTO = OrderItemMapper.toOrderItemDTO(orderItemDao);
        assertEquals(orderItemDao.getOrderItemId(), orderItemDTO.getOrderItemId());
        assertEquals(orderItemDao.getQuantity(), orderItemDTO.getQuantity());
        assertEquals(orderItemDao.getCreatedAt(), orderItemDTO.getCreatedAt());
        assertEquals(orderItemDao.getOrder().getOrderId(), orderItemDTO.getOrderId());
        assertEquals(orderItemDao.getItem(), orderItemDTO.getItem());
    }

    @Test
    void toOrderItemDao() {
        OrderItemDTO orderItemDTO = createSampleOrderItemDTO();
        OrderItemDao orderItemDao = OrderItemMapper.toOrderItemDao(orderItemDTO);
        assertEquals(orderItemDTO.getOrderItemId(), orderItemDao.getOrderItemId());
        assertEquals(orderItemDTO.getQuantity(), orderItemDao.getQuantity());
        assertEquals(orderItemDTO.getCreatedAt(), orderItemDao.getCreatedAt());
        assertEquals(orderItemDTO.getOrderId(), orderItemDao.getOrder().getOrderId());
        assertEquals(orderItemDTO.getItem(), orderItemDao.getItem());
    }

    private OrderItemDao createSampleOrderItemDao() {
        OrderItemDao orderItemDao = new OrderItemDao();
        orderItemDao.setOrderItemId(1L);
        orderItemDao.setQuantity(2);
        orderItemDao.setCreatedAt(new Date());
        OrderDao orderDao = new OrderDao();
        orderDao.setOrderId(101L);
        orderItemDao.setOrder(orderDao);

        return orderItemDao;
    }

    private OrderItemDTO createSampleOrderItemDTO() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(1L);
        orderItemDTO.setQuantity(2);
        orderItemDTO.setCreatedAt(new Date());
        orderItemDTO.setOrderId(101L);

        return orderItemDTO;
    }
}
