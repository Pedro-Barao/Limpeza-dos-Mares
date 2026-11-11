package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EstadoDTO;
import com.example.demo.entities.Estado;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.mapper.EstadoMapper;
import com.example.demo.repository.IEstadoRepository;

@Service
@SuppressWarnings("null")
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

    public EstadoDTO buscaPorId(Long id)
    {

        return estadoMapper.toDTO(buscarEntidade(id));

    }

    @Transactional
    public EstadoDTO salvar(EstadoDTO estadoDTO)
    {

        if (estadoDTO == null)
        {

            throw new IllegalArgumentException("O estado não pode ser nulo");

        }

        Estado estado = estadoMapper.toEntity(estadoDTO);

        estado.setSigla(estado.getSigla().toUpperCase());

        return estadoMapper.toDTO(estadoRepository.save(estado));

    }

    @Transactional
    public EstadoDTO atualizar(Long id, EstadoDTO estadoDTO)
    {

        if (estadoDTO == null)
        {

            throw new IllegalArgumentException("O estado não pode ser nulo");

        }

        Estado estadoAtualizado = buscarEntidade(id);

        estadoAtualizado.setNome(estadoDTO.getNome());
        estadoAtualizado.setSigla(estadoDTO.getSigla().toUpperCase());

        return estadoMapper.toDTO(estadoRepository.save(estadoAtualizado));

    }

    @Transactional
    public EstadoDTO atualizarParcialmente(Long id, EstadoDTO estadoDTO)
    {

        if (estadoDTO == null)
        {

            throw new IllegalArgumentException("O estado não pode ser nulo");

        }

        Estado estadoAtualizado = buscarEntidade(id);

        if(estadoDTO.getNome() != null) { estadoAtualizado.setNome(estadoDTO.getNome()); }

        if(estadoDTO.getSigla() != null)
        {

            // Sem @Valid no PATCH, então o tamanho da sigla é validado aqui
            if(estadoDTO.getSigla().trim().length() != 2)
            {

                throw new IllegalArgumentException("A sigla deve ter exatamente 2 letras");

            }

            estadoAtualizado.setSigla(estadoDTO.getSigla().trim().toUpperCase());

        }

        return estadoMapper.toDTO(estadoRepository.save(estadoAtualizado));

    }

    @Transactional
    public void deletar(Long id)
    {

        estadoRepository.delete(buscarEntidade(id));

    }

    private Estado buscarEntidade(Long id)
    {

        return estadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estado não encontrado com o id: " + id));

    }

}
