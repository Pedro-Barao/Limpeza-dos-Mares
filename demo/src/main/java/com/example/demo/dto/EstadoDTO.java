package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstadoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "A sigla é obrigatória")
    @Size(min = 2, max = 2, message = "A sigla deve ter exatamente 2 letras")
    private String sigla;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

}
