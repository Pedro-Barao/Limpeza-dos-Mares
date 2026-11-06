package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstadoDTO {

    private Long id;

    @NotBlank(message = "A sigla é obrigatória")
    private String sigla;
    
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    
}
