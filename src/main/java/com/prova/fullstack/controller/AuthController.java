package com.prova.fullstack.controller;

import com.prova.fullstack.dto.TokenRefreshRequest;
import com.prova.fullstack.dto.UserRequestDTO;
import com.prova.fullstack.entity.RefreshToken;
import com.prova.fullstack.security.AuthRequest;
import com.prova.fullstack.security.AuthResponse;
import com.prova.fullstack.security.CustomUserDetails;
import com.prova.fullstack.security.JwtUtil;
import com.prova.fullstack.service.RefreshTokenService;
import com.prova.fullstack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

            String accessToken = jwtUtil.generateToken(userDetails);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("error", "Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(userService.salvar(dto));
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("error", "Usuário não autenticado"));
        }
        return ResponseEntity.ok(Map.of(
                "id", userDetails.getId(),
                "email", userDetails.getUsername()
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid TokenRefreshRequest request) {
        RefreshToken token = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        refreshTokenService.verifyExpiration(token);

        String newAccessToken = jwtUtil.generateTokenFromUsername(token.getUser().getEmail());
        return ResponseEntity.ok(new AuthResponse(newAccessToken, token.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid TokenRefreshRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
