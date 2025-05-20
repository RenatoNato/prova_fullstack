package com.prova.fullstack.service;

import com.prova.fullstack.client.ViaCepClient;
import com.prova.fullstack.dto.AddressDTO;
import com.prova.fullstack.dto.AddressRequestDTO;
import com.prova.fullstack.dto.ViaCepResponseDTO;
import com.prova.fullstack.entity.Address;
import com.prova.fullstack.entity.User;
import com.prova.fullstack.repository.AddressRepository;
import com.prova.fullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repositorioEndereco;
    private final UserRepository repositorioUsuario;
    private final ViaCepClient viaCepClient;

    public AddressDTO salvarEndereco(AddressRequestDTO dto) {
        User usuario = repositorioUsuario.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ViaCepResponseDTO cepInfo = viaCepClient.buscarEnderecoPorCep(dto.getCep());

        if (cepInfo == null || cepInfo.getLogradouro() == null) {
            throw new RuntimeException("CEP inválido");
        }

        Address endereco = new Address();
        endereco.setLogradouro(cepInfo.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento() != null ? dto.getComplemento() : cepInfo.getComplemento());
        endereco.setBairro(cepInfo.getBairro());
        endereco.setCidade(cepInfo.getLocalidade());
        endereco.setEstado(cepInfo.getUf());
        endereco.setCep(cepInfo.getCep());
        endereco.setUser(usuario);

        return converterParaDTO(repositorioEndereco.save(endereco));
    }

    public Page<AddressDTO> listarTodosEnderecos(Pageable pageable) {
        return repositorioEndereco.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public Page<AddressDTO> listarEnderecosPorUsuario(Long usuarioId, Pageable pageable) {
        return repositorioEndereco.findByUserId(usuarioId, pageable)
                .map(this::converterParaDTO);
    }

    public AddressDTO atualizarEndereco(Long id, AddressRequestDTO dto) {
        Address endereco = repositorioEndereco.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        User usuario = repositorioUsuario.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ViaCepResponseDTO cepInfo = viaCepClient.buscarEnderecoPorCep(dto.getCep());

        if (cepInfo == null || cepInfo.getLogradouro() == null) {
            throw new RuntimeException("CEP inválido");
        }

        endereco.setLogradouro(cepInfo.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento() != null ? dto.getComplemento() : cepInfo.getComplemento());
        endereco.setBairro(cepInfo.getBairro());
        endereco.setCidade(cepInfo.getLocalidade());
        endereco.setEstado(cepInfo.getUf());
        endereco.setCep(cepInfo.getCep());
        endereco.setUser(usuario);

        return converterParaDTO(repositorioEndereco.save(endereco));
    }

    public void deletarEndereco(Long id) {
        repositorioEndereco.deleteById(id);
    }

    private AddressDTO converterParaDTO(Address endereco) {
        AddressDTO dto = new AddressDTO();
        BeanUtils.copyProperties(endereco, dto);
        dto.setUserId(endereco.getUser().getId());
        return dto;
    }
}
