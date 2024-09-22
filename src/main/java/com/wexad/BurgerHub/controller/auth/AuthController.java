package com.wexad.BurgerHub.controller.auth;

import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.security.JwtTokenUtil;
import com.wexad.BurgerHub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Handles authentication-related operations like login, signup, and token refresh.")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AuthUserService authUserService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService, RefreshTokenService refreshTokenService, RefreshTokenService refreshTokenService1, AuthUserService authUserService, CategoryService categoryService, ProductService productService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService1;
        this.authUserService = authUserService;
        this.categoryService = categoryService;
        this.productService = productService;
    }


    public Tokens getToken(@RequestBody AuthUserDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authenticationToken);
        authUserService.isDeleted(user);
        return refreshTokenService.getTokens(user);
    }

    @Operation(summary = "Generate tokens. Get all products and categories", description = "Generates access and refresh tokens for the authenticated user. Email didn't need")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens generated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> getTokens(@RequestBody AuthUserDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authenticationToken);
        authUserService.isDeleted(user);
        Tokens tokens = refreshTokenService.getTokens(user);
        List<CategoryDataDTO> categories = categoryService.findAll();
        List<ProductDataDTO> products = productService.getProducts();
        return ResponseEntity.ok(new LoginDTO(tokens, categories, products));
    }


    @Operation(summary = "Register a new user", description = "Registers a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping("/signup")
    public void register(@RequestBody AuthUserDTO user) {
        authUserService.save(user);
        ResponseEntity.status(HttpStatus.CREATED).body("Successfully registered");
    }

    @Operation(summary = "Refresh tokens", description = "Refreshes the access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token")
    })
    @PostMapping("/refresh")
    public Tokens refresh(@RequestBody Tokens tokens) {
        return refreshTokenService.refresh(tokens);
    }
}
