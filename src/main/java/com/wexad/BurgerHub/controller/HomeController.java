package com.wexad.BurgerHub.controller;

import com.wexad.BurgerHub.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    private final RoleService roleService;

    public HomeController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Spring Security Refresh Token";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String user() {
        return "User";
    }



}
