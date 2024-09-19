package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}