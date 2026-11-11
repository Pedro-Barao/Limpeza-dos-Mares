package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Endereco;

import java.util.List;


public interface IEnderecoRepository extends JpaRepository<Endereco, Long>{

    List<Endereco> findByCepContainingIgnoreCase(String cep);

    List<Endereco> findByLogradouroContainingIgnoreCase(String logradouro);

    List<Endereco> findByBairroContainingIgnoreCase(String bairro);

    List<Endereco> findByCidadeContainingIgnoreCase(String cidade);

}
