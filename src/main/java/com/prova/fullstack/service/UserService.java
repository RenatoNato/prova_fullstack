package com.prova.fullstack.service;

import com.prova.fullstack.dto.AddressDTO;
import com.prova.fullstack.dto.UserDTO;
import com.prova.fullstack.dto.UserRequestDTO;
import com.prova.fullstack.entity.User;
import com.prova.fullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<UserDTO> listar(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::toDTO);
    }


    public UserDTO salvar(UserRequestDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        return toDTO(userRepository.save(user));
    }

    public UserDTO buscarPorId(Long id) {
        return userRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UserDTO atualizar(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            user.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        }

        return toDTO(userRepository.save(user));
    }

    public void deletar(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);

        if (user.getEnderecos() != null) {
            List<AddressDTO> enderecosDTO = user.getEnderecos()
                    .stream()
                    .map(endereco -> {
                        AddressDTO enderecoDTO = new AddressDTO();
                        BeanUtils.copyProperties(endereco, enderecoDTO);
                        enderecoDTO.setUserId(user.getId());
                        return enderecoDTO;
                    }).collect(Collectors.toList());
            dto.setEnderecos(enderecosDTO);
        }

        return dto;
    }
}
