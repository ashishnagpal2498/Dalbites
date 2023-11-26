package com.asdc.dalbites.model.DTO;

import com.asdc.dalbites.model.ENUMS.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO {
    private OrderStatusEnum status;
}
