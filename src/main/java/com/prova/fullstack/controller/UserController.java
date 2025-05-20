package com.prova.fullstack.controller;

import com.prova.fullstack.dto.UserDTO;
import com.prova.fullstack.dto.UserRequestDTO;
import com.prova.fullstack.security.CustomUserDetails;
import com.prova.fullstack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Só ADMIN pode listar todos.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDTO> listar(Pageable pageable) {
        return userService.listar(pageable);
    }

    /**
     * ADMIN pode ver qualquer usuário;
     * usuário comum só o próprio.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public UserDTO buscarPorId(@PathVariable Long id) {
        return userService.buscarPorId(id);
    }

    /**
     * Retorna os dados do próprio usuário autenticado.
     */
    @GetMapping("/me")
    public UserDTO quemSou(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.buscarPorId(userDetails.getId());
    }

    /**
     * Apenas ADMIN pode criar outros usuários.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO salvar(@RequestBody @Valid UserRequestDTO dto) {
        return userService.salvar(dto);
    }

    /**
     * ADMIN pode atualizar qualquer usuário;
     * usuário comum só pode atualizar o próprio.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public UserDTO atualizar(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto) {
        return userService.atualizar(id, dto);
    }

    /**
     * Apenas ADMIN pode deletar usuários.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
