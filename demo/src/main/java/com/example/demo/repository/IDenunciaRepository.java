package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Denuncia;
import com.example.demo.enums.TipoResiduo;
import com.example.demo.enums.GrauPerigo;

import java.util.List;

public interface IDenunciaRepository extends JpaRepository<Denuncia, Long> {

    List<Denuncia> findByTextoContainingIgnoreCase(String texto);

    List<Denuncia> findByTipoResiduo(TipoResiduo tipoResiduo);

    List<Denuncia> findByGrauPerigo(GrauPerigo grauPerigo);

}