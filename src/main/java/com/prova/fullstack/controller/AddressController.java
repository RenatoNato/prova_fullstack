package com.prova.fullstack.controller;

import com.prova.fullstack.dto.AddressDTO;
import com.prova.fullstack.dto.AddressRequestDTO;
import com.prova.fullstack.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public AddressDTO criar(@RequestBody @Valid AddressRequestDTO dto) {
        return addressService.salvarEndereco(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AddressDTO> listarTodos(Pageable pageable) {
        return addressService.listarTodosEnderecos(pageable);
    }

    @GetMapping("/usuario/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<AddressDTO> listarPorUsuario(@PathVariable Long userId, Pageable pageable) {
        return addressService.listarEnderecosPorUsuario(userId, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public AddressDTO atualizar(@PathVariable Long id, @RequestBody @Valid AddressRequestDTO dto) {
        return addressService.atualizarEndereco(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletar(@PathVariable Long id) {
        addressService.deletarEndereco(id);
    }
}
