package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}