package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DenunciaDTO;
import com.example.demo.service.DenunciaService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

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
        
        List<DenunciaDTO> denuncias = denunciaService.listarTodos();

        return ResponseEntity.ok(denuncias);

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca uma denuncia por texto", description = "Retorna os detalhes de uma denuncia")
    @GetMapping("/{id}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorTexto(@PathVariable String texto)
    {

        List<DenunciaDTO> denuncia = denunciaService.buscaPorTexto(texto);

        if(denuncia.isEmpty())
        {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(denuncia);
        
    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca uma denuncia por texto", description = "Retorna os detalhes de uma denuncia")
    @GetMapping("/{id}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorTipoResiduo(@PathVariable String tipoResiduo)
    {

        List<DenunciaDTO> denuncia = denunciaService.buscaPorTipoResiduo(tipoResiduo);

        if(denuncia.isEmpty())
        {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(denuncia);
        
    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca uma denuncia por texto", description = "Retorna os detalhes de uma denuncia")
    @GetMapping("/{id}")
    public ResponseEntity<List<DenunciaDTO>> buscarPorGrauPerigo(@PathVariable String grauPerigo)
    {

        List<DenunciaDTO> denuncia = denunciaService.buscaPorTexto(grauPerigo);

        if(denuncia.isEmpty())
        {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(denuncia);
        
    }

    @Operation(summary = "Altera uma denuncia", description = "Altera uma denuncia no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DenunciaDTO>> alterarCliente(@PathVariable Long id, @Valid @RequestBody DenunciaDTO denunciaDTO)
    {

        try {

            DenunciaDTO novaDenuncia = denunciaService.atualizar(id, denunciaDTO);

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(novaDenuncia);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Cliente não encontrado", e.getMessage());

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Altera um denuncia", description = "Altera um denuncia no sistema")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DenunciaDTO>> alterarParcialmenteCliente(@PathVariable Long id, @Valid @RequestBody DenunciaDTO denunciaDTO)
    {

        try {

            DenunciaDTO novaDenuncia = denunciaService.atualizarParcialmente(id, denunciaDTO);

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(novaDenuncia);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Cliente não encontrado", e.getMessage());

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<DenunciaDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }
    
    @Operation(summary = "Deleta uma denuncia", description = "Remove uma denuncia do sistema pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDenuncia(@PathVariable Long id)
    {

        denunciaService.deletar(id);

        return ResponseEntity.noContent().build();

    }

}
