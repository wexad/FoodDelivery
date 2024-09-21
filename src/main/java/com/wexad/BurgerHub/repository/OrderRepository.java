package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from orders where user = ?1 ")
    List<Order> findByUserId(AuthUser user);
}