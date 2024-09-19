package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}