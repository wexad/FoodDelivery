package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.UserDTO;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin User Controller", description = "Admin operations for managing users.")
public class UserController {
    private final RoleService roleService;
    private final AuthUserService authUserService;

    public UserController(RoleService roleService, AuthUserService authUserService) {
        this.roleService = roleService;
        this.authUserService = authUserService;
    }

    @Operation(summary = "Add Admin role to a user", description = "Assigns Admin role to a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin role added successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/addAdmin/{id}/")
    public ResponseEntity<String> addAdmin(@PathVariable Long id) {
        roleService.addAdminRoleToUser(id);
        return ResponseEntity.ok("Admin role added to user with id: " + id);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user by providing their details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody AuthUserDTO user) {
        authUserService.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        authUserService.deleteUser(id);
        return ResponseEntity.ok("User with id: " + id + " deleted");
    }

    @Operation(summary = "Get user details by ID", description = "Retrieves the details of a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(authUserService.getUserById(id));

    }
    @Operation(summary = "List all users", description = "Retrieves a list of all users in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        return ResponseEntity.ok(authUserService.getAllUsers());
    }
}
