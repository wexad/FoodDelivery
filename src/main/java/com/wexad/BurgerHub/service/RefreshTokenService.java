package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.AuthUserDTO;
import com.wexad.BurgerHub.dto.Tokens;
import com.wexad.BurgerHub.model.RefreshToken;
import com.wexad.BurgerHub.repository.RefreshTokenRepository;
import com.wexad.BurgerHub.security.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserService authUserService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final static Integer REFRESH_TOKEN_EXPIRES_IN = 1000 * 60 * 60;
    private final static Integer ACCESS_TOKEN_EXPIRES_IN = 1000 * 60;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AuthUserService authUserService, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authUserService = authUserService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    public void save(String refreshToken, Long id) {
        refreshTokenRepository.save(
                RefreshToken.builder().
                        token(refreshToken).
                        userId(id).
                        build()
        );
    }

    public void findByToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Could not find Token"));
    }

    public Tokens getTokens(AuthUserDTO user) {
        String refreshToken = jwtTokenUtil.generateToken(user.username(), REFRESH_TOKEN_EXPIRES_IN);
        String accessToken = jwtTokenUtil.generateToken(user.username(), ACCESS_TOKEN_EXPIRES_IN);
        Long userId = getUserIdWithUsername(user.username());
        System.out.println(userId + " " + user.username());
        deleteWithUserId(userId);
        save(refreshToken, userId);
        return new Tokens(accessToken, refreshToken);
    }

    private void deleteWithUserId(Long id) {
        refreshTokenRepository.deleteWithUserId(id);
    }

    public Long getUserIdWithUsername(String username) {
        return authUserService.getIdWithUsername(username);
    }

    public Tokens refresh(Tokens tokens) {
        String username = jwtTokenUtil.extractUsername(tokens.refreshToken());
        jwtTokenUtil.validateToken(tokens.refreshToken(),
                customUserDetailsService.loadUserByUsername(username));
        findByToken(tokens.refreshToken());
        return new Tokens(jwtTokenUtil.generateToken(username, ACCESS_TOKEN_EXPIRES_IN), tokens.refreshToken());
    }
}
