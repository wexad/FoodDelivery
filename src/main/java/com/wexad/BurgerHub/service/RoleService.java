package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import com.wexad.BurgerHub.repository.RoleRepository;
import com.wexad.BurgerHub.security.SessionUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthUserService authUserService;
    private final SessionUser sessionUser;

    public RoleService(RoleRepository roleRepository, @Lazy AuthUserService authUserService,@Lazy SessionUser sessionUser) {
        this.roleRepository = roleRepository;
        this.authUserService = authUserService;
        this.sessionUser = sessionUser;
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


    public List<String> getRoles(String username) {
        List<String> roles = new ArrayList<>();
        AuthUser authUser = authUserService.findByUsername(authUserService.getUserById(authUserService.getIdWithUsername(username)).username()).orElseThrow(() -> new RuntimeException("User not found"));
        Arrays.stream(authUser.getRoles().toArray(new Role[0]))
                .map(role -> role.getName().toString())
                .forEach(roles::add);
        return roles;
    }
}
