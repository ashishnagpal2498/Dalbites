package com.asdc.dalbites.service;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.OrderDao;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public OrderDao getOrder(Long orderId) throws ResourceNotFoundException;
}
