package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.UserDTO;
import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.handler.exceptions.UserDeletedException;
import com.wexad.BurgerHub.mapper.UserMapper;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Role;
import com.wexad.BurgerHub.repository.AuthUserRepository;
import com.wexad.BurgerHub.DataInitializer;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.wexad.BurgerHub.mapper.UserMapper.USER_MAPPER;

@Service
public class AuthUserService implements CommandLineRunner {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DataInitializer dataInitializer;

    public AuthUserService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, RoleService roleService, DataInitializer dataInitializer) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.dataInitializer = dataInitializer;
    }

    public void save(AuthUserDTO user) {
        AuthUser authUser = USER_MAPPER.toEntity(user);
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.USER).get());
        authUser.setRoles(roles);
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUserRepository.save(authUser);
    }

    @Transactional
    public void save(AuthUser user) {
        authUserRepository.save(user);
    }

    public Long getIdWithUsername(String username) {
        return authUserRepository.getIdWithUsername(username);
    }

    public Optional<AuthUser> findById(Long userId) {
        return authUserRepository.findById(userId);
    }

    public Optional<AuthUser> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    @Override
    public void run(String... args) throws Exception {


    }

    public void deleteUser(Long id) {
        authUserRepository.updateDeletedBy(id);
    }

    public List<UserDTO> getAllUsers() {
        return USER_MAPPER.toUserDtoList(authUserRepository.findAll());
    }

    public UserDTO getUserById(Long id) {
        return UserMapper.toUserDto(findById(id).orElseGet(() ->
                AuthUser.builder()
                        .id(0L)
                        .username("none")
                        .roles(new HashSet<>())
                        .email("none")
                        .deleted(false)
                        .build()
        ));
    }

    public void isDeleted(AuthUserDTO user) {
        if (authUserRepository.isDeleted(USER_MAPPER.toEntity(user).getUsername())) {
            throw new UserDeletedException("User is deleted");
        }
    }

}
