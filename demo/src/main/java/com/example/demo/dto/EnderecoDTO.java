package com.example.demo.dto;

import com.example.demo.entities.Estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnderecoDTO {
    
    private Long id;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 9, max = 9, message = "O CEP tem que ter obrigatóriamente 9 dígitos")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 2, max = 80, message = "O logradouro deve ter obrigatóriamente entre 2 e 80 caracteres")
    private String logradouro;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(min = 2, max = 72, message = "O bairro deve ter obrigatóriamente entre 2 e 72 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 2, max = 60, message = "A ciade deve ter obrigatóriamente entre 2 e 60 caracteres")
    private String cidade;

    @NotNull(message = "")
    private Estado estado;

}
