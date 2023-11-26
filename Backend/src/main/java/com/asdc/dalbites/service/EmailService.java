package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for sending emails.
 */
@Service
public interface EmailService {

    /**
     * Sends an email to the specified recipient with the given subject and text.
     *
     * @param to      The email address of the recipient.
     * @param subject The subject of the email.
     * @param text    The content or body of the email.
     */
    public void sendEmail(String to, String subject, String text);
    void sendOrderConfirmationEmail(OrderDTO orderDTO, List<Optional<MenuItemDao>> menuItems, String to);
}