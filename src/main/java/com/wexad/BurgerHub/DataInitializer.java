package com.wexad.BurgerHub;

import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import com.wexad.BurgerHub.repository.AuthUserRepository;
import com.wexad.BurgerHub.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    private final AuthUserRepository authUserRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder, AuthUserRepository authUserRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authUserRepository = authUserRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {

            createRoleIfNotFound(RoleName.ADMIN, "Administrator role with full access");
        }

        if (roleRepository.findByName(RoleName.USER).isEmpty()) {
            createRoleIfNotFound(RoleName.USER, "Regular user role");
        }

        if (roleRepository.findByName(RoleName.CUSTOMER).isEmpty()) {
            createRoleIfNotFound(RoleName.CUSTOMER, "Customer role with limited access");
        }
        Role adminRole = createRoleIfNotFound(RoleName.ADMIN, "Administrator role with full access");
        Role userRole = createRoleIfNotFound(RoleName.USER, "Regular user role");

        if (authUserRepository.findByUsername("sys").isEmpty()) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);

            AuthUser adminUser = AuthUser.builder()
                    .username("sys")
                    .password(passwordEncoder.encode("123"))
                    .email("admin@gmail.com")
                    .roles(roles)
                    .build();

            authUserRepository.save(adminUser);
            System.out.println("Admin user created with username: sys");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    public Role createRoleIfNotFound(RoleName roleName, String description) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    newRole.setDescription(description);
                    return roleRepository.save(newRole);
                });
    }
}

