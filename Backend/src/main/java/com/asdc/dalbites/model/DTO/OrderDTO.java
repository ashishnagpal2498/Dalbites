package com.asdc.dalbites.model.DTO;

import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private List<OrderItemDTO> orderItems;
    private Double totalAmount;
    private OrderStatusEnum status;
    private String specialInstruction;
    private Date createdAt;
    private Long userId;
    private Long restaurantId;
}
