package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Category;
import com.wexad.BurgerHub.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product a set a.deleted = true where a.id = ?1")
    void updateDeletedBy(Long id);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") Category category);

    @Transactional
    @Modifying
    @Query("update Product a set a.deleted = false where a.id = ?1")
    void updateRestoredBy(Long id);
}