package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.model.Compound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompoundRepository extends JpaRepository<Compound, Long> {
}