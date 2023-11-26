package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.OrderDTO;
import com.asdc.dalbites.model.DTO.OrderItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService = new EmailServiceImpl();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSendEmail_Success() {
        String to = "customer@example.com";
        String subject = "Test Subject";
        String text = "Test Message";
        emailService.sendEmail(to, subject, text);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendOrderConfirmationEmail_Success() {

        OrderDTO order = new OrderDTO();
        order.setOrderId(1L);
        order.setTotalAmount(20.0);
        OrderItemDTO orderItem1 = new OrderItemDTO();
        OrderItemDTO orderItem2 = new OrderItemDTO();

        MenuItemDao menuItem1 = new MenuItemDao();
        menuItem1.setId(1L);
        menuItem1.setName("Burger");

        MenuItemDao menuItem2 = new MenuItemDao();
        menuItem2.setId(2L);
        menuItem2.setName("Pizza");
        orderItem1.setItem(menuItem1);
        orderItem2.setItem(menuItem2);
        List<OrderItemDTO> orderItems = Arrays.asList(orderItem1,orderItem2);
        order.setOrderItems(orderItems);
        List<Optional<MenuItemDao>> menuItems = Arrays.asList(Optional.of(menuItem1), Optional.of(menuItem2));

        String to = "customer@example.com";

        emailService.sendOrderConfirmationEmail(order, menuItems, to);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
