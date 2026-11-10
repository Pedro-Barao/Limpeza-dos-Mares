package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.entities.Denuncia;
import com.example.demo.enums.TipoResiduo;
import com.example.demo.enums.GrauPerigo;

import java.util.List;

public interface IDenunciaRepository extends JpaRepository<Denuncia, Long> {

    List<DenunciaDTO> findByTextoContainingIgnoreCase(String texto);

    List<DenunciaDTO> findByTipoResiduo(TipoResiduo tipoResiduo);

    List<DenunciaDTO> findByGrauPerigo(GrauPerigo grauPerigo);

}