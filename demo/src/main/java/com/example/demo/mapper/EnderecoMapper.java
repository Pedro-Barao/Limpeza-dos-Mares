package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.entities.Endereco;

@Mapper(componentModel = "spring", uses = EstadoMapper.class)
public interface EnderecoMapper {

    @Mapping(target = "estadoId", source = "estado.id")
    EnderecoDTO toDTO(Endereco endereco);

    // O estado é buscado no banco pelo service a partir do estadoId
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Endereco toEntity(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> toDTOList(List<Endereco> enderecos);

}
