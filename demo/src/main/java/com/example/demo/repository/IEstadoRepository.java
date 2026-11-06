package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Long> {
    
}
