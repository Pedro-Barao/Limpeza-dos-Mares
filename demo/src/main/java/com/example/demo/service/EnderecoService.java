package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.entities.Endereco;
import com.example.demo.mapper.EnderecoMapper;
import com.example.demo.repository.IEnderecoRepository;

@Service
public class EnderecoService {

    private final IEnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    
    public EnderecoService(IEnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) 
    {

        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        
    }

    public List<EnderecoDTO> listarTodos() 
    {

        return enderecoMapper.toDTOList(enderecoRepository.findAll());

    }

    public Optional<EnderecoDTO> buscaPorId(@NonNull Long id) 
    {

        return enderecoRepository.findById(id).map(enderecoMapper::toDTO);

    }

    @SuppressWarnings("null")
    public EnderecoDTO salvar(EnderecoDTO enderecoDTO) 
    {

        if (enderecoDTO == null) 
        {

            throw new IllegalArgumentException("\nO endereço não pode ser nulo");

        }

        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);

        return enderecoMapper.toDTO(enderecoRepository.save(endereco));

    }

    @SuppressWarnings("null")
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) 
    {

        if (enderecoDTO == null) 
        {

            throw new IllegalArgumentException("\nO endereço não pode ser nulo");

        }

        Endereco enderecoAtualizado = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEndereço não encontrado com o id: " + id));

        enderecoAtualizado.setCep(enderecoDTO.getCep());
        enderecoAtualizado.setLogradouro(enderecoDTO.getLogradouro());
        enderecoAtualizado.setBairro(enderecoDTO.getBairro());
        enderecoAtualizado.setCidade(enderecoDTO.getCidade());
        enderecoAtualizado.setComplemento(enderecoDTO.getComplemento());
        
        enderecoAtualizado.setEstado(enderecoMapper.toEstadoEntity(enderecoDTO.getEstado()));

        return enderecoMapper.toDTO(enderecoRepository.save(enderecoAtualizado));

    }

    @SuppressWarnings("null")
    public EnderecoDTO atualizarParcialmente(Long id, EnderecoDTO enderecoDTO) 
    {

        if (enderecoDTO == null) 
        {

            throw new IllegalArgumentException("\nO endereço não pode ser nulo");

        }

        Endereco enderecoAtualizado = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEndereço não encontrado com o id: " + id));

        if(enderecoDTO.getCep() != null) { enderecoAtualizado.setCep(enderecoDTO.getCep()); }
        if(enderecoDTO.getLogradouro() != null) { enderecoAtualizado.setLogradouro(enderecoDTO.getLogradouro()); }
        if(enderecoDTO.getBairro() != null) { enderecoAtualizado.setBairro(enderecoDTO.getBairro()); }
        if(enderecoDTO.getCidade() != null) { enderecoAtualizado.setCidade(enderecoDTO.getCidade()); }
        if(enderecoDTO.getComplemento() != null) { enderecoAtualizado.setComplemento(enderecoDTO.getComplemento()); }
        if(enderecoDTO.getEstado() != null) { enderecoAtualizado.setEstado(enderecoMapper.toEstadoEntity(enderecoDTO.getEstado())); }

        return enderecoMapper.toDTO(enderecoRepository.save(enderecoAtualizado));

    }

    @SuppressWarnings("null")
    public void deletar(Long id) 
    {

        Endereco enderecoDeletado = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEndereço não encontrado com o id: " + id));

        enderecoRepository.delete(enderecoDeletado);

    }
}