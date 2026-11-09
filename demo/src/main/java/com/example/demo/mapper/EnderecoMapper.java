package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.dto.EstadoDTO;
import com.example.demo.entities.Endereco;
import com.example.demo.entities.Estado;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDTO toDTO(Endereco endereco);

    @Mapping(target = "estado", ignore = true)
    Endereco toEntity(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> toDTOList(List<Endereco> enderecos);

    Estado toEstadoEntity(EstadoDTO estadoDTO);

}
