package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import com.wexad.BurgerHub.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;

    public RoleService(RoleRepository roleRepository, @Lazy AuthUserService authUserService, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.roleRepository = roleRepository;
        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder1;
    }

    public void addAdminRoleToUser(Long userId) {
        AuthUser user = authUserService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role adminRole = findByName(RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        user.getRoles().add(adminRole);
        authUserService.save(user);
    }

    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }


}
