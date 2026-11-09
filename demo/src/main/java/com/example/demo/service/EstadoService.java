package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EstadoDTO;
import com.example.demo.entities.Estado;
import com.example.demo.mapper.EstadoMapper;
import com.example.demo.repository.IEstadoRepository;

@Service
public class EstadoService {

    private final IEstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;
    
    public EstadoService(IEstadoRepository estadoRepository, EstadoMapper estadoMapper) 
    {

        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;

    }

    public List<EstadoDTO> listarTodos() 
    {

        return estadoMapper.toDTOList(estadoRepository.findAll());

    }

    public Optional<EstadoDTO> buscaPorId(@NonNull Long id) 
    {

        return estadoRepository.findById(id).map(estadoMapper::toDTO);

    }

    @SuppressWarnings("null")
    public EstadoDTO salvar(EstadoDTO estadoDTO) 
    {

        if (estadoDTO == null) 
        {

            throw new IllegalArgumentException("\nO estado não pode ser nulo");

        }

        Estado estado = estadoMapper.toEntity(estadoDTO);

        return estadoMapper.toDTO(estadoRepository.save(estado));

    }

    @SuppressWarnings("null")
    public EstadoDTO atualizar(Long id, EstadoDTO estadoDTO) 
    {

        if (estadoDTO == null) 
        {

            throw new IllegalArgumentException("\nO estado não pode ser nulo");

        }

        Estado estadoAtualizado = estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEstado não encontrado com o id: " + id));


        estadoAtualizado.setNome(estadoDTO.getNome());
        estadoAtualizado.setSigla(estadoDTO.getSigla());

        return estadoMapper.toDTO(estadoRepository.save(estadoAtualizado));

    }

    @SuppressWarnings("null")
    public EstadoDTO atualizarParcialmente(Long id, EstadoDTO estadoDTO) 
    {

        if (estadoDTO == null) 
        {

            throw new IllegalArgumentException("\nO estado não pode ser nulo");

        }

        Estado estadoAtualizado = estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEstado não encontrado com o id: " + id));

        if(estadoDTO.getNome() != null) { estadoAtualizado.setNome(estadoDTO.getNome()); }
        if(estadoDTO.getSigla() != null) { estadoAtualizado.setSigla(estadoDTO.getSigla()); }

        return estadoMapper.toDTO(estadoRepository.save(estadoAtualizado));

    }

    @SuppressWarnings("null")
    public void deletar(Long id) 
    {

        Estado estadoDeletado = estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("\nEstado não encontrado com o id: " + id));

        estadoRepository.delete(estadoDeletado);

    }
    
}