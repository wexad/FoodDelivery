package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.Tokens;
import com.wexad.BurgerHub.model.AuthUser;
import com.wexad.BurgerHub.model.RefreshToken;
import com.wexad.BurgerHub.repository.RefreshTokenRepository;
import com.wexad.BurgerHub.security.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserService authUserService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private static final Integer REFRESH_TOKEN_EXPIRES_IN = 1000 * 60 * 60 * 24 * 7;
    private static final Integer ACCESS_TOKEN_EXPIRES_IN = 1000 * 60 * 5;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AuthUserService authUserService, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authUserService = authUserService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;

    }

    public void save(String refreshToken, Long userId) {
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .userId(userId)
                        .build()
        );
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new IllegalArgumentException("Refresh token not found.")
        );
    }

    public Tokens getTokens(AuthUserDTO user) {
        Long userId = getUserIdWithUsername(user.username());
        AuthUser authUser = authUserService.findById(userId).orElseThrow(() -> new IllegalArgumentException("Auth user not found."));
        Set<String> roles = authUser.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        String refreshToken = jwtTokenUtil.generateToken(user.username(), REFRESH_TOKEN_EXPIRES_IN, roles);
        String accessToken = jwtTokenUtil.generateToken(user.username(), ACCESS_TOKEN_EXPIRES_IN, roles);
        deleteWithUserId(userId);
        save(refreshToken, userId);
        return new Tokens(accessToken, refreshToken);
    }

    private void deleteWithUserId(Long userId) {
        refreshTokenRepository.deleteWithUserId(userId);
    }

    public Long getUserIdWithUsername(String username) {
        return authUserService.getIdWithUsername(username);
    }

    public Tokens refresh(Tokens tokens) {
        String username = jwtTokenUtil.extractUsername(tokens.refreshToken());
        jwtTokenUtil.validateToken(tokens.refreshToken(),
                customUserDetailsService.loadUserByUsername(username));
        findByToken(tokens.refreshToken());
        Long userId = getUserIdWithUsername(username);
        AuthUser authUser = authUserService.findById(userId).orElseThrow(() -> new IllegalArgumentException("Auth user not found."));
        Set<String> roles = authUser.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        String newAccessToken = jwtTokenUtil.generateToken(username, ACCESS_TOKEN_EXPIRES_IN, roles);

        return new Tokens(newAccessToken, tokens.refreshToken());
    }
}
