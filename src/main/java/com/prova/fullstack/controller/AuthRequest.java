package com.prova.fullstack.controller;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
}
