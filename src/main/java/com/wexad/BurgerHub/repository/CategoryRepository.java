package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}