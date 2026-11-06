package com.example.demo.entities;

import com.example.demo.enums.GrauPerigo;
import com.example.demo.enums.TipoResiduo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Denuncia")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Denuncia {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private TipoResiduo tipoResiduo;

    @Column(nullable = false)
    private GrauPerigo grauPerigo;

    @ManyToOne
    @JoinColumn(name = "enderecoid", nullable = false)
    private Endereco idEndereco;
    
}
