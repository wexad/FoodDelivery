package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product a set a.deleted = true where a.id = ?1")
    void updateDeletedBy(Long id);
}