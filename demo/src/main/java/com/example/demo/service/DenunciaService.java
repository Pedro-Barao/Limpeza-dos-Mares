package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.entities.Denuncia;
import com.example.demo.entities.Endereco;
import com.example.demo.enums.GrauPerigo;
import com.example.demo.enums.TipoResiduo;
import com.example.demo.exception.RecursoNaoEncontradoException;
import com.example.demo.mapper.DenunciaMapper;
import com.example.demo.repository.IDenunciaRepository;
import com.example.demo.repository.IEnderecoRepository;

@Service
@SuppressWarnings("null")
public class DenunciaService {

    private final IDenunciaRepository denunciaRepository;
    private final IEnderecoRepository enderecoRepository;
    private final DenunciaMapper denunciaMapper;

    public DenunciaService(IDenunciaRepository denunciaRepository, IEnderecoRepository enderecoRepository, DenunciaMapper denunciaMapper)
    {

        this.denunciaRepository = denunciaRepository;
        this.enderecoRepository = enderecoRepository;
        this.denunciaMapper = denunciaMapper;

    }

    public List<DenunciaDTO> listarTodos()
    {

        return denunciaMapper.toDTOList(denunciaRepository.findAll());

    }

    public DenunciaDTO buscaPorId(Long id)
    {

        return denunciaMapper.toDTO(buscarEntidade(id));

    }

    public List<DenunciaDTO> buscaPorTexto(String texto)
    {

        return denunciaMapper.toDTOList(denunciaRepository.findByTextoContainingIgnoreCase(texto));

    }

    public List<DenunciaDTO> buscaPorTipoResiduo(String tipoResiduo)
    {

        return denunciaMapper.toDTOList(denunciaRepository.findByTipoResiduo(TipoResiduo.deTexto(tipoResiduo)));

    }

    public List<DenunciaDTO> buscaPorGrauPerigo(String grauPerigo)
    {

        return denunciaMapper.toDTOList(denunciaRepository.findByGrauPerigo(GrauPerigo.deTexto(grauPerigo)));

    }

    @Transactional
    public DenunciaDTO salvar(DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("A denuncia não pode ser nula");

        }

        Denuncia denuncia = denunciaMapper.toEntity(denunciaDTO);

        denuncia.setEndereco(buscarEndereco(denunciaDTO.getEnderecoId()));

        return denunciaMapper.toDTO(denunciaRepository.save(denuncia));

    }

    @Transactional
    public DenunciaDTO atualizar(Long id, DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("A denuncia não pode ser nula");

        }

        Denuncia denunciaAtualizada = buscarEntidade(id);

        denunciaAtualizada.setEndereco(buscarEndereco(denunciaDTO.getEnderecoId()));
        denunciaAtualizada.setGrauPerigo(denunciaDTO.getGrauPerigo());
        denunciaAtualizada.setTexto(denunciaDTO.getTexto());
        denunciaAtualizada.setTipoResiduo(denunciaDTO.getTipoResiduo());

        return denunciaMapper.toDTO(denunciaRepository.save(denunciaAtualizada));

    }

    @Transactional
    public DenunciaDTO atualizarParcialmente(Long id, DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("A denuncia não pode ser nula");

        }

        Denuncia denunciaAtualizada = buscarEntidade(id);

        if(denunciaDTO.getEnderecoId() != null) { denunciaAtualizada.setEndereco(buscarEndereco(denunciaDTO.getEnderecoId())); }
        if(denunciaDTO.getGrauPerigo() != null) { denunciaAtualizada.setGrauPerigo(denunciaDTO.getGrauPerigo()); }
        if(denunciaDTO.getTipoResiduo() != null) { denunciaAtualizada.setTipoResiduo(denunciaDTO.getTipoResiduo()); }

        if(denunciaDTO.getTexto() != null)
        {

            // Sem @Valid no PATCH, então o tamanho do texto é validado aqui
            int tamanho = denunciaDTO.getTexto().trim().length();

            if(tamanho < 20 || tamanho > 500)
            {

                throw new IllegalArgumentException("A quantidade de caracteres presentes no campo deve ser entre 20 e 500");

            }

            denunciaAtualizada.setTexto(denunciaDTO.getTexto());

        }

        return denunciaMapper.toDTO(denunciaRepository.save(denunciaAtualizada));

    }

    @Transactional
    public void deletar(Long id)
    {

        denunciaRepository.delete(buscarEntidade(id));

    }

    private Denuncia buscarEntidade(Long id)
    {

        return denunciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Denuncia não encontrada com o id: " + id));

    }

    private Endereco buscarEndereco(Long enderecoId)
    {

        if(enderecoId == null)
        {

            throw new IllegalArgumentException("O id do endereço é obrigatório");

        }

        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado com o id: " + enderecoId));

    }

}
