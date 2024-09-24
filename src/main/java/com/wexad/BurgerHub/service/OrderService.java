package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.enums.PaymentType;
import com.wexad.BurgerHub.handler.exceptions.RequiredAddressException;
import com.wexad.BurgerHub.handler.exceptions.UserNotFoundException;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Order;
import com.wexad.BurgerHub.model.Payment;
import com.wexad.BurgerHub.model.Product;
import com.wexad.BurgerHub.repository.OrderRepository;
import com.wexad.BurgerHub.security.SessionUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthUserService authUserService;
    private final SessionUser sessionUser;
    private final PaymentService paymentService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository, AuthUserService authUserService, SessionUser sessionUser, PaymentService paymentService, ProductService productService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.authUserService = authUserService;
        this.sessionUser = sessionUser;
        this.paymentService = paymentService;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    public void save(CardDTO cardDTO) {
        AuthUser authUser = authUserService.findById(sessionUser.id())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (authUser.getAddress() == null) {
            throw new RequiredAddressException("First, you have to enter your address on your profile.");
        }
        LocalDateTime now = LocalDateTime.now();
        Payment payment = new Payment(PaymentType.CARD, cardDTO.cardNumber(), now);
        paymentService.save(payment);
        List<OrderItemDTO> orderItems = orderItemService.getOrderItems();
        for (OrderItemDTO order : orderItems) {
            Product product = productService.getProduct(order.productId());
            Order ordered = new Order(product, payment, authUser, order.count());
            orderRepository.save(ordered);
        }
        orderItemService.deleteCardItems();
    }

    public Map<LocalDateTime, List<ArchiveDTO>> getOrders() {
        List<Order> orders = orderRepository.findByUserId(sessionUser.user());
        Map<LocalDateTime, List<ArchiveDTO>> map = new HashMap<>();
        for (Order order : orders) {
            LocalDateTime orderedDate = order.getPayment().getPaymentDate();
            ArchiveDTO archiveDTO = new ArchiveDTO(
                    order.getProduct().getName(),
                    order.getProduct().getDescription(),
                    order.getProduct().getPrice(),
                    order.getCount(),
                    order.getProduct().getImage().getPath()
            );
            map.computeIfAbsent(orderedDate, k -> new ArrayList<>()).add(archiveDTO);
        }

        return map;
    }
}
