package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.entities.Endereco;
import com.example.demo.entities.Estado;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.mapper.EnderecoMapper;
import com.example.demo.repository.IEnderecoRepository;
import com.example.demo.repository.IEstadoRepository;

@Service
@SuppressWarnings("null")
public class EnderecoService {

    private final IEnderecoRepository enderecoRepository;
    private final IEstadoRepository estadoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoService(IEnderecoRepository enderecoRepository, IEstadoRepository estadoRepository, EnderecoMapper enderecoMapper)
    {

        this.enderecoRepository = enderecoRepository;
        this.estadoRepository = estadoRepository;
        this.enderecoMapper = enderecoMapper;

    }

    public List<EnderecoDTO> listarTodos()
    {

        return enderecoMapper.toDTOList(enderecoRepository.findAll());

    }

    public EnderecoDTO buscaPorId(Long id)
    {

        return enderecoMapper.toDTO(buscarEntidade(id));

    }

    public List<EnderecoDTO> buscaPorCep(String cep)
    {

        return enderecoMapper.toDTOList(enderecoRepository.findByCepContainingIgnoreCase(cep));

    }

    public List<EnderecoDTO> buscaPorLogradouro(String logradouro)
    {

        return enderecoMapper.toDTOList(enderecoRepository.findByLogradouroContainingIgnoreCase(logradouro));

    }

    public List<EnderecoDTO> buscaPorBairro(String bairro)
    {

        return enderecoMapper.toDTOList(enderecoRepository.findByBairroContainingIgnoreCase(bairro));

    }

    public List<EnderecoDTO> buscaPorCidade(String cidade)
    {

        return enderecoMapper.toDTOList(enderecoRepository.findByCidadeContainingIgnoreCase(cidade));

    }

    @Transactional
    public EnderecoDTO salvar(EnderecoDTO enderecoDTO)
    {

        if (enderecoDTO == null)
        {

            throw new IllegalArgumentException("O endereço não pode ser nulo");

        }

        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);

        endereco.setEstado(buscarEstado(enderecoDTO.getEstadoId()));

        return enderecoMapper.toDTO(enderecoRepository.save(endereco));

    }

    @Transactional
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO)
    {

        if (enderecoDTO == null)
        {

            throw new IllegalArgumentException("O endereço não pode ser nulo");

        }

        Endereco enderecoAtualizado = buscarEntidade(id);

        enderecoAtualizado.setCep(enderecoDTO.getCep());
        enderecoAtualizado.setLogradouro(enderecoDTO.getLogradouro());
        enderecoAtualizado.setBairro(enderecoDTO.getBairro());
        enderecoAtualizado.setCidade(enderecoDTO.getCidade());
        enderecoAtualizado.setComplemento(enderecoDTO.getComplemento());
        enderecoAtualizado.setEstado(buscarEstado(enderecoDTO.getEstadoId()));

        return enderecoMapper.toDTO(enderecoRepository.save(enderecoAtualizado));

    }

    @Transactional
    public EnderecoDTO atualizarParcialmente(Long id, EnderecoDTO enderecoDTO)
    {

        if (enderecoDTO == null)
        {

            throw new IllegalArgumentException("O endereço não pode ser nulo");

        }

        Endereco enderecoAtualizado = buscarEntidade(id);

        if(enderecoDTO.getCep() != null)
        {

            // Sem @Valid no PATCH, então o formato do CEP é validado aqui
            if(!enderecoDTO.getCep().matches("\\d{5}-\\d{3}"))
            {

                throw new IllegalArgumentException("O CEP deve estar no formato 00000-000");

            }

            enderecoAtualizado.setCep(enderecoDTO.getCep());

        }

        if(enderecoDTO.getLogradouro() != null) { enderecoAtualizado.setLogradouro(enderecoDTO.getLogradouro()); }
        if(enderecoDTO.getBairro() != null) { enderecoAtualizado.setBairro(enderecoDTO.getBairro()); }
        if(enderecoDTO.getCidade() != null) { enderecoAtualizado.setCidade(enderecoDTO.getCidade()); }
        if(enderecoDTO.getComplemento() != null) { enderecoAtualizado.setComplemento(enderecoDTO.getComplemento()); }
        if(enderecoDTO.getEstadoId() != null) { enderecoAtualizado.setEstado(buscarEstado(enderecoDTO.getEstadoId())); }

        return enderecoMapper.toDTO(enderecoRepository.save(enderecoAtualizado));

    }

    @Transactional
    public void deletar(Long id)
    {

        enderecoRepository.delete(buscarEntidade(id));

    }

    private Endereco buscarEntidade(Long id)
    {

        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado com o id: " + id));

    }

    private Estado buscarEstado(Long estadoId)
    {

        if(estadoId == null)
        {

            throw new IllegalArgumentException("O id do estado é obrigatório");

        }

        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estado não encontrado com o id: " + estadoId));

    }

}
