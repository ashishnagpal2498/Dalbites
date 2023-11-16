package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.OrderItemDao;
import com.asdc.dalbites.model.DTO.OrderItemDTO;

public class OrderItemMapper {

    public static OrderItemDTO toOrderItemDTO(OrderItemDao orderItemDao) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(orderItemDao.getOrderItemId());
        orderItemDTO.setQuantity(orderItemDao.getQuantity());
        orderItemDTO.setCreatedAt(orderItemDao.getCreatedAt());
        orderItemDTO.setOrderId(orderItemDao.getOrder().getOrderId());
        orderItemDTO.setItem(orderItemDao.getItem());
        return orderItemDTO;
    }

    public static OrderItemDao toOrderItemDao(OrderItemDTO orderItemDTO) {
        OrderItemDao orderItemDao = new OrderItemDao();
        orderItemDao.setOrderItemId(orderItemDTO.getOrderItemId());
        orderItemDao.setQuantity(orderItemDTO.getQuantity());
        orderItemDao.setCreatedAt(orderItemDTO.getCreatedAt());
        OrderDao order = new OrderDao();
        order.setOrderId(orderItemDTO.getOrderId());
        orderItemDao.setOrder(order);
        orderItemDao.setItem(orderItemDTO.getItem());
        return orderItemDao;
    }

}
