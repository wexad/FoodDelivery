package com.wexad.BurgerHub.controller;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.Tokens;
import com.wexad.BurgerHub.repository.AuthUserRepository;
import com.wexad.BurgerHub.security.JwtTokenUtil;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.CustomUserDetailsService;
import com.wexad.BurgerHub.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AuthUserService authUserService;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService, RefreshTokenService refreshTokenService, RefreshTokenService refreshTokenService1, AuthUserService authUserService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService1;
        this.authUserService = authUserService;
    }

    @PostMapping("/login")
    public Tokens getToken(@RequestBody AuthUserDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authenticationToken);
        return refreshTokenService.getTokens(user);
    }

    @PostMapping("/signup")
    public void register(@RequestBody AuthUserDTO user) {
        authUserService.save(user);
    }

    @PostMapping("/refresh")
    public Tokens refresh(@RequestBody Tokens tokens) {
        return refreshTokenService.refresh(tokens);
    }
}
