package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.EstadoDTO;
import com.example.demo.entities.Estado;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

    EstadoDTO toDTO(Estado estado);

    @Mapping(target = "id", ignore = true)
    Estado toEntity(EstadoDTO estadoDTO);

    List<EstadoDTO> toDTOList(List<Estado> estados);

}
