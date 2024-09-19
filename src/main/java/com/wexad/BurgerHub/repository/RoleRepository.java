package com.wexad.BurgerHub.repository;

import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}