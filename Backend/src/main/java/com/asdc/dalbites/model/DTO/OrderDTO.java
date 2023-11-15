package com.asdc.dalbites.model.DTO;

import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private List<OrderItemDTO> orderItems;
    private Double totalAmount;
    private OrderStatusEnum status;
    private String specialInstruction;
    private Date createdAt;
    private Long userId;
    private Long restaurantId;
}
