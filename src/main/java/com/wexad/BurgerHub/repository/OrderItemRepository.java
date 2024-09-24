package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM order_item oi WHERE oi.user.id = :id")
    List<OrderItem> findAllByUserId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE from order_item  oi where oi.user.id = :userId")
    void deleteByUserId(Long userId);

}