package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.model.Payment;
import com.wexad.BurgerHub.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
