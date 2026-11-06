package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.entities.Denuncia;

@Mapper(componentModel = "spring")
public interface DenunciaMapper {

    DenunciaDTO toDTO(Denuncia denuncia);

    Denuncia toEntity(DenunciaDTO denunciaDTO);

    List<DenunciaDTO> toDTOList(List<Denuncia> denuncias);

}
