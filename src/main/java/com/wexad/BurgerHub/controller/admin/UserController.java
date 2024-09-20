package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.UserDTO;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final RoleService roleService;
    private final AuthUserService authUserService;

    public UserController(RoleService roleService, AuthUserService authUserService) {
        this.roleService = roleService;
        this.authUserService = authUserService;
    }

    @PostMapping("/addAdmin/{id}/")
    public ResponseEntity<String> addAdmin(@PathVariable Long id) {
        roleService.addAdminRoleToUser(id);
        return ResponseEntity.ok("Admin role added to user with id: " + id);
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody AuthUserDTO user) {
        authUserService.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        authUserService.deleteUser(id);
        return ResponseEntity.ok("User with id: " + id + " deleted");
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(authUserService.getUserById(id));

    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        return ResponseEntity.ok(authUserService.getAllUsers());
    }
}
