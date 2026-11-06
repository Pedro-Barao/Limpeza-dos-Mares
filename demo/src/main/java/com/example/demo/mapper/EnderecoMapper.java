package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.entities.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDTO toDTO(Endereco endereco);

    Endereco toEntity(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> toDTOList(List<Endereco> denuncias);

}
