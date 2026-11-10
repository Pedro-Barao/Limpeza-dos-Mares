package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.entities.Endereco;

import java.util.List;


public interface IEnderecoRepository extends JpaRepository<Endereco, Long>{
    
    List<EnderecoDTO> findByCepContainingIgnoreCase(String cep);

    List<EnderecoDTO> findByLogradouroContainingIgnoreCase(String logradouro);

    List<EnderecoDTO> findByBairroContainingIgnoreCase(String bairro);

    List<EnderecoDTO> findByCidadeContainingIgnoreCase(String cidade);

}
