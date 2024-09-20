package com.wexad.BurgerHub.controller.auth;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.Tokens;
import com.wexad.BurgerHub.security.JwtTokenUtil;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.CustomUserDetailsService;
import com.wexad.BurgerHub.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
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


    public Tokens getToken(@RequestBody AuthUserDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authenticationToken);
        authUserService.isDeleted(user);
        return refreshTokenService.getTokens(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> getTokens(@RequestBody AuthUserDTO user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authenticationToken);
        authUserService.isDeleted(user);
        Tokens tokens = refreshTokenService.getTokens(user);
        return ResponseEntity.ok(tokens);
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
