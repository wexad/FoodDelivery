package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.enums.RoleName;
import com.wexad.BurgerHub.handler.exceptions.PasswordIncorrectException;
import com.wexad.BurgerHub.handler.exceptions.UserDeletedException;
import com.wexad.BurgerHub.handler.exceptions.UserNotFoundException;
import com.wexad.BurgerHub.mapper.UserMapper;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.Image;
import com.wexad.BurgerHub.model.Role;
import com.wexad.BurgerHub.repository.AuthUserRepository;
import com.wexad.BurgerHub.security.SessionUser;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.wexad.BurgerHub.mapper.AddressMapper.ADDRESS_MAPPER;
import static com.wexad.BurgerHub.mapper.UserAddressMapper.USER_ADDRESS_MAPPER;
import static com.wexad.BurgerHub.mapper.UserDataMapper.USER_DATA_MAPPER;
import static com.wexad.BurgerHub.mapper.UserMapper.USER_MAPPER;

@Service
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final SessionUser sessionUser;

    private static final String USER_URL = "user";
    private final StorageService storageService;
    private final ImageService imageService;

    public AuthUserService(AuthUserRepository authUserRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService,
                           @Lazy SessionUser sessionUser,
                           ImageService imageService,
                           StorageService storageService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.sessionUser = sessionUser;
        this.storageService = storageService;
        this.imageService = imageService;
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


    public void deleteUser(Long id) {
        UserMapper.toUserDto(findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found")));
        authUserRepository.updateDeletedBy(id);
    }
    public void restoreUser(Long id) {
        UserMapper.toUserDto(findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found")));
        authUserRepository.updateRestoredBy(id);
    }

    public List<UserAddressDTO> getAllUsers() {
        List<AuthUser> users = authUserRepository.findAll();
        List<UserAddressDTO> result = USER_ADDRESS_MAPPER.toDTO(users);

        result.forEach(userAddressDTO -> {
            AuthUser user = users.stream()
                    .filter(u -> u.getId().equals(userAddressDTO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            String roleName = user.getRoles().size() > 1 ? RoleName.ADMIN.name() : RoleName.USER.name();
            userAddressDTO.setRole(roleName);
        });
        return result;
    }

    public UserDTO getUserById(Long id) {
        return UserMapper.toUserDto(findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found")
        ));
    }

    public void isDeleted(AuthUserDTO user) {
        if (authUserRepository.isDeleted(USER_MAPPER.toEntity(user).getUsername())) {
            throw new UserDeletedException("User is deleted");
        }
    }

    public UserDataDTO getUserData() {
        return USER_DATA_MAPPER.toUserDataDTO(authUserRepository.findById(sessionUser.user().getId()).orElseThrow(() -> new UserNotFoundException("User Not Found")));
    }

    public void saveImage(MultipartFile file) {
        String path = storageService.uploadFile(file, USER_URL);
        Image image = new Image(path);
        imageService.save(image);
        AuthUser authUser = authUserRepository.findById(sessionUser.user().getId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        authUser.setImage(image);
        authUserRepository.save(authUser);
    }

    public void resetPassword(PasswordDTO passwordDTO) {
        if (!passwordEncoder.matches(passwordDTO.oldPassword(), sessionUser.user().getPassword())) {
            throw new PasswordIncorrectException("Old password is incorrect");
        }
        authUserRepository.updatePasswordByPassword(passwordEncoder.encode(passwordDTO.newPassword()), sessionUser.id());
    }

    public void changeAddress(AddressDTO addressDTO) {
        AuthUser authUser = authUserRepository.findById(sessionUser.id()).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        authUser.setAddress(ADDRESS_MAPPER.toEntity(addressDTO));
        authUserRepository.save(authUser);
    }

}
