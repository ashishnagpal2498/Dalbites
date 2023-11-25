package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmailService {
    public void sendEmail(String to, String subject, String text);
    void sendOrderConfirmationEmail(OrderDTO orderDTO, List<Optional<MenuItemDao>> menuItems, String to);
}