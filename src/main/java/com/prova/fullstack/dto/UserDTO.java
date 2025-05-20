package com.prova.fullstack.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private List<AddressDTO> enderecos;
}
