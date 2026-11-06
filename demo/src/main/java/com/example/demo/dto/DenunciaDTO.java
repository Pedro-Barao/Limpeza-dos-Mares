package com.example.demo.dto;

import com.example.demo.entities.Endereco;
import com.example.demo.enums.GrauPerigo;
import com.example.demo.enums.TipoResiduo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class DenunciaDTO {
    
    private Long Denuncia_Id;

    @NotBlank(message = "O texto de denuncia é obrigatório")
    @Size(min = 20, max = 500, message = "A quantidade de caracteres presentes no campo deve ser entre 20 e 500")
    private String texto;

    @NotNull(message = "O tipo do resíduo é obrigatrio")
    private TipoResiduo tipoResiduo;

    @NotNull(message = "O grau de perigo do resíduo é obrigatório")
    private GrauPerigo grauPerigo;

    @NotNull(message = "O local de visualização do resíduo é obtrigatório")
    private Endereco idEndereco;

}
