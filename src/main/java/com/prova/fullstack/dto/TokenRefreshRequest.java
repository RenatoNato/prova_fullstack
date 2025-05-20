package com.prova.fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body para logout (invalidação do refresh token).
 */
@Data
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;
}
