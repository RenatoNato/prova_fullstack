package com.prova.fullstack.dto;

import lombok.Data;

@Data
public class ViaCepResponseDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // cidade
    private String uf;         // estado
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}
