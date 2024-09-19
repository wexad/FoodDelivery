package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}