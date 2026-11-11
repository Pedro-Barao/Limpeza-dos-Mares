package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnderecoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(min = 2, max = 80, message = "O logradouro deve ter obrigatoriamente entre 2 e 80 caracteres")
    private String logradouro;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(min = 2, max = 72, message = "O bairro deve ter obrigatoriamente entre 2 e 72 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 2, max = 60, message = "A cidade deve ter obrigatoriamente entre 2 e 60 caracteres")
    private String cidade;

    private String complemento;

    /** Id do estado (usado na entrada). */
    @NotNull(message = "O estado é obrigatório")
    private Long estadoId;

    /** Dados completos do estado (somente na resposta). */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EstadoDTO estado;

}
