package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Transactional
    @Modifying
    @Query("update Category a set a.deleted = true where a.id = ?1")
    void updateDeletedBy(Long id);
    @Transactional
    @Modifying
    @Query("update Category a set a.deleted = false where a.id = ?1")
    void updateRestoredBy(Long id);
}