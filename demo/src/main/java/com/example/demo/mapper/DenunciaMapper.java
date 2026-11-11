package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.entities.Denuncia;

@Mapper(componentModel = "spring", uses = EnderecoMapper.class)
public interface DenunciaMapper {

    @Mapping(target = "enderecoId", source = "endereco.id")
    DenunciaDTO toDTO(Denuncia denuncia);

    // O endereço é buscado no banco pelo service a partir do enderecoId
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Denuncia toEntity(DenunciaDTO denunciaDTO);

    List<DenunciaDTO> toDTOList(List<Denuncia> denuncias);

}
