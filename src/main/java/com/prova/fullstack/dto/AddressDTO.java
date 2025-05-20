package com.prova.fullstack.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Novo campo adicionado
    private Long userId;



}
