package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Transactional
    @Modifying
    @Query("update Restaurant a set a.deleted = true where a.id = ?1")
    void updateDeletedBy(Long id);

    @Transactional
    @Modifying
    @Query("update Restaurant a set a.deleted = false where a.id = ?1")
    void updateRestoredBy(Long id);
}