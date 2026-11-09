package com.example.demo.service;

import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.entities.Denuncia;
import com.example.demo.mapper.DenunciaMapper;
import com.example.demo.repository.IDenunciaRepository;

@Service
public class DenunciaService {

    private final IDenunciaRepository denunciaRepository;
    private final DenunciaMapper denunciaMapper;
    
    public DenunciaService(IDenunciaRepository denunciaRepository, DenunciaMapper denunciaMapper)
    {

        this.denunciaRepository = denunciaRepository;
        this.denunciaMapper = denunciaMapper;

    }

    public List<DenunciaDTO> listarTodos()
    {

        return denunciaMapper.toDTOList(denunciaRepository.findAll());
        
    }

    public List<DenunciaDTO> buscaPorTexto(@NonNull String texto)
    {

        List<DenunciaDTO> denuncias = denunciaRepository.findByTextoContainingIgnoreCase(texto);

        if(denuncias.isEmpty())
        {

            throw new RuntimeException("\nNenhuma denuncia encontrada com o texto: " + texto);

        }

        return denuncias;

    }

    public List<DenunciaDTO> buscaPorTipoResiduo(@NonNull String tipoResiduo)
    {

        List<DenunciaDTO> denuncias = denunciaRepository.findByTextoContainingIgnoreCase(tipoResiduo);

        if(denuncias.isEmpty())
        {

            throw new RuntimeException("\nNenhuma denuncia encontrada com o Tipo de Resíduo: " + tipoResiduo);

        }

        return denuncias;

    }

    public List<DenunciaDTO> buscaPorGrauPerigo(@NonNull String grauPerigo)
    {

        List<DenunciaDTO> denuncias = denunciaRepository.findByTextoContainingIgnoreCase(grauPerigo);

        if(denuncias.isEmpty())
        {

            throw new RuntimeException("\nNenhuma denuncia encontrada com o Grau Perigo: " + grauPerigo);

        }

        return denuncias;

    }

    @SuppressWarnings("null")
    public DenunciaDTO salvar(DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("\nA denuncia não pode ser nula");

        }

        Denuncia denuncia = denunciaMapper.toEntity(denunciaDTO);

        return denunciaMapper.toDTO(denunciaRepository.save(denuncia));

    }

    @SuppressWarnings("null")
    public DenunciaDTO atualizar(Long id, DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("\nA denuncia não pode ser nula");

        }

        Denuncia denunciaAtualizada = denunciaRepository.findById(id).orElseThrow(() -> new RuntimeException("\nDenuncia não encontrada com o id: " + id));

        denunciaAtualizada.setIdEndereco(denunciaDTO.getIdEndereco());
        denunciaAtualizada.setGrauPerigo(denunciaDTO.getGrauPerigo());
        denunciaAtualizada.setTexto(denunciaDTO.getTexto());
        denunciaAtualizada.setTipoResiduo(denunciaDTO.getTipoResiduo());

        return denunciaMapper.toDTO(denunciaRepository.save(denunciaAtualizada));

    }

    @SuppressWarnings("null")
    public DenunciaDTO atualizarParcialmente(Long id, DenunciaDTO denunciaDTO)
    {

        if(denunciaDTO == null)
        {

            throw new IllegalArgumentException("\nA denuncia não pode ser nula");

        }

        Denuncia denunciaAtualizada = denunciaRepository.findById(id).orElseThrow(() -> new RuntimeException("\nDenuncia não encontrada com o id: " + id));

        if(denunciaDTO.getIdEndereco() != null) { denunciaAtualizada.setIdEndereco(denunciaDTO.getIdEndereco()); }
        if(denunciaDTO.getGrauPerigo() != null) { denunciaAtualizada.setGrauPerigo(denunciaDTO.getGrauPerigo()); }
        if(denunciaDTO.getTexto() != null) { denunciaAtualizada.setTexto(denunciaDTO.getTexto()); }
        if(denunciaDTO.getTipoResiduo() != null) { denunciaAtualizada.setTipoResiduo(denunciaDTO.getTipoResiduo()); }

        return denunciaMapper.toDTO(denunciaRepository.save(denunciaAtualizada));

    }

    @SuppressWarnings("null")
    public void deletar(Long id)
    {

        Denuncia denunciaDeletada = denunciaRepository.findById(id).orElseThrow(() -> new RuntimeException("\nDenuncia não encontrada com o id: " + id));

        denunciaRepository.delete(denunciaDeletada);
        
    }
    
}
