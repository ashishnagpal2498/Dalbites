package com.asdc.dalbites.model.DTO;

import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for status of the order
 */
@Getter
@Setter
public class OrderStatusDTO {
    private OrderStatusEnum status;
}
