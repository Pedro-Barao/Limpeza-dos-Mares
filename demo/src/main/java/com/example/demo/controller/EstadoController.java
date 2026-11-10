package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EstadoDTO;
import com.example.demo.service.EstadoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Estado", description = "Endpoints para gerenciamento de estados")
@RestController
@RequestMapping("/api/estados")
@CrossOrigin("*")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {

        this.estadoService = estadoService;

    }

    @Operation(summary = "Lista todos os estados", description = "Mostra todos os estados do sistema")
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listarEstados() {

        List<EstadoDTO> estados = estadoService.listarTodos();

        return ResponseEntity.ok(estados);

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca um estado por ID", description = "Retorna os detalhes de um estado")
    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscarPorId(@PathVariable Long id) {

        return estadoService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Cadastra um estado", description = "Adiciona um novo estado ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<EstadoDTO>> cadastrarEstado(@Valid @RequestBody EstadoDTO estadoDTO) {

        try {

            EstadoDTO novoEstado = estadoService.salvar(estadoDTO);

            ApiResponse<EstadoDTO> response = new ApiResponse<>(novoEstado);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro ao cadastrar estado", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Altera um estado", description = "Altera um estado no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoDTO>> alterarEstado(@PathVariable Long id, @Valid @RequestBody EstadoDTO estadoDTO) {

        try {

            EstadoDTO novoEstado = estadoService.atualizar(id, estadoDTO);

            ApiResponse<EstadoDTO> response = new ApiResponse<>(novoEstado);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Estado não encontrado", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Altera parcialmente um estado", description = "Altera parcialmente um estado no sistema")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoDTO>> alterarParcialmenteEstado(@PathVariable Long id, @Valid @RequestBody EstadoDTO estadoDTO) {

        try {

            EstadoDTO novoEstado = estadoService.atualizarParcialmente(id, estadoDTO);

            ApiResponse<EstadoDTO> response = new ApiResponse<>(novoEstado);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Estado não encontrado", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EstadoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Deleta um estado", description = "Remove um estado do sistema pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstado(@PathVariable Long id) {

        estadoService.deletar(id);

        return ResponseEntity.noContent().build();

    }

}