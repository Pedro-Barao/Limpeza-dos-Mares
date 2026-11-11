package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.service.DenunciaService;
import com.example.demo.service.Utils.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Denuncia", description = "Endpoints para gerenciamento de denuncias")
@RestController
@RequestMapping("/api/denuncias")
@CrossOrigin("*")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService)
    {

        this.denunciaService = denunciaService;

    }

    @Operation(summary = "Lista todas as denuncias", description = "Mostra todas as denuncias do sistema")
    @GetMapping
    public ResponseEntity<List<DenunciaDTO>> listarDenuncias()
    {

        return ResponseEntity.ok(denunciaService.listarTodos());

    }

    @Operation(summary = "Busca uma denuncia por ID", description = "Retorna os detalhes de uma denuncia")
    @GetMapping("/{id}")
    public ResponseEntity<DenunciaDTO> buscarPorId(@PathVariable Long id)
    {

        return ResponseEntity.ok(denunciaService.buscaPorId(id));

    }

    @Operation(summary = "Busca denuncias por texto", description = "Retorna as denuncias cujo texto contém o termo informado")
    @GetMapping("/buscar/texto/{texto}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorTexto(@PathVariable String texto)
    {

        return ResponseEntity.ok(denunciaService.buscaPorTexto(texto));

    }

    @Operation(summary = "Busca denuncias por tipo de resíduo", description = "Valores aceitos: SOLIDO, LIQUIDO, PASTOSO")
    @GetMapping("/buscar/tipo_residuo/{tipoResiduo}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorTipoResiduo(@PathVariable String tipoResiduo)
    {

        return ResponseEntity.ok(denunciaService.buscaPorTipoResiduo(tipoResiduo));

    }

    @Operation(summary = "Busca denuncias por grau de perigo", description = "Valores aceitos: BAIXO, MEDIO, ALTO")
    @GetMapping("/buscar/grau_perigo/{grauPerigo}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorGrauPerigo(@PathVariable String grauPerigo)
    {

        return ResponseEntity.ok(denunciaService.buscaPorGrauPerigo(grauPerigo));

    }

    @Operation(summary = "Cadastra uma denuncia", description = "Registra uma nova denuncia no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<DenunciaDTO>> cadastrarDenuncia(@Valid @RequestBody DenunciaDTO denunciaDTO)
    {

        DenunciaDTO novaDenuncia = denunciaService.salvar(denunciaDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(novaDenuncia));

    }

    @Operation(summary = "Altera uma denuncia", description = "Altera todos os dados de uma denuncia no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DenunciaDTO>> alterarDenuncia(@PathVariable Long id, @Valid @RequestBody DenunciaDTO denunciaDTO)
    {

        DenunciaDTO denunciaAtualizada = denunciaService.atualizar(id, denunciaDTO);

        return ResponseEntity.ok(new ApiResponse<>(denunciaAtualizada));

    }

    @Operation(summary = "Altera parcialmente uma denuncia", description = "Altera apenas os campos enviados de uma denuncia")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DenunciaDTO>> alterarParcialmenteDenuncia(@PathVariable Long id, @RequestBody DenunciaDTO denunciaDTO)
    {

        DenunciaDTO denunciaAtualizada = denunciaService.atualizarParcialmente(id, denunciaDTO);

        return ResponseEntity.ok(new ApiResponse<>(denunciaAtualizada));

    }

    @Operation(summary = "Deleta uma denuncia", description = "Remove uma denuncia do sistema pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDenuncia(@PathVariable Long id)
    {

        denunciaService.deletar(id);

        return ResponseEntity.noContent().build();

    }

}
