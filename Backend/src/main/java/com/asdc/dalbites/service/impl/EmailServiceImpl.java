package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.asdc.dalbites.service.EmailService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link EmailService} interface providing methods to send emails.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    protected JavaMailSender javaMailSender;

    /**
     * Sends an email using the configured {@link JavaMailSender}.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param text    The content of the email.
     */
    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Override
    public void sendOrderConfirmationEmail(OrderDTO order, List<Optional<MenuItemDao>> menuItems, String to){
        StringBuilder emailText = new StringBuilder();
        emailText.append("Dear Customer,\n\n")
                .append("Thank you for placing your order with Dalbites. Here are the details of your order:\n\n");

        emailText.append("Order Items:\n");
        for (int i=0;i<order.getOrderItems().size(); i++) {
            OrderItemDTO orderItem = order.getOrderItems().get(i);
            emailText.append("- ")
                    .append(orderItem.getQuantity())
                    .append(" x ")
                    .append(menuItems.get(i).get().getName())
                    .append(" ")
                    .append("\n");
        }
        emailText.append("\nTotal Cost: $")
                .append(order.getTotalAmount())
                .append("\n");
        emailText.append("\nPayment Method: Online ")
                .append("\n\n");

        emailText.append("Thank you for choosing Dalbites! We appreciate your business.\n\n")
                .append("Best Regards,\nDalbites Team");

        String subject = "Dalbites | Order Confirmation - #" + order.getOrderId();
        sendEmail(to, subject, emailText.toString());
    }
}
