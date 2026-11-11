package com.example.demo.dto;

import com.example.demo.enums.GrauPerigo;
import com.example.demo.enums.TipoResiduo;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DenunciaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "O texto de denuncia é obrigatório")
    @Size(min = 20, max = 500, message = "A quantidade de caracteres presentes no campo deve ser entre 20 e 500")
    private String texto;

    @NotNull(message = "O tipo do resíduo é obrigatório")
    private TipoResiduo tipoResiduo;

    @NotNull(message = "O grau de perigo do resíduo é obrigatório")
    private GrauPerigo grauPerigo;

    /** Id do endereço onde o resíduo foi visto (usado na entrada). */
    @NotNull(message = "O local de visualização do resíduo é obrigatório")
    private Long enderecoId;

    /** Dados completos do endereço (somente na resposta). */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EnderecoDTO endereco;

}
