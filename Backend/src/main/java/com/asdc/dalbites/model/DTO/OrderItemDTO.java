package com.asdc.dalbites.model.DTO;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import lombok.Data;

import java.util.Date;

@Data
public class OrderItemDTO {
    private Long orderItemId;
    private int quantity;
    private Date createdAt;
    private Long orderId;
    private MenuItemDao item;
}
