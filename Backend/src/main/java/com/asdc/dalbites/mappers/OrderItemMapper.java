package com.asdc.dalbites.mappers;

import com.asdc.dalbites.model.DAO.OrderDao;
import com.asdc.dalbites.model.DAO.OrderItemDao;
import com.asdc.dalbites.model.DTO.OrderItemDTO;

/**
 * Class responsible for mapping between OrderItemDao and OrderItemDTO.
 */
public class OrderItemMapper {

    /**
     * Converts OrderItemDao to OrderItemDTO.
     *
     * @param orderItemDao The OrderItemDao object to be converted.
     * @return The corresponding OrderItemDTO object.
     */
    public static OrderItemDTO toOrderItemDTO(OrderItemDao orderItemDao) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(orderItemDao.getOrderItemId());
        orderItemDTO.setQuantity(orderItemDao.getQuantity());
        orderItemDTO.setCreatedAt(orderItemDao.getCreatedAt());
        orderItemDTO.setOrderId(orderItemDao.getOrder().getOrderId());
        orderItemDTO.setItem(orderItemDao.getItem());
        return orderItemDTO;
    }

    /**
     * Converts OrderItemDTO to OrderItemDao.
     *
     * @param orderItemDTO The OrderItemDTO object to be converted.
     * @return The corresponding OrderItemDao object.
     */
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
