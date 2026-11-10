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

import com.example.demo.dto.EnderecoDTO;
import com.example.demo.service.EnderecoService;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Endereco", description = "Endpoints para gerenciamento de endereços")
@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin("*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {

        this.enderecoService = enderecoService;

    }

    @Operation(summary = "Lista todos os endereços", description = "Mostra todos os endereços do sistema")
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() {

        List<EnderecoDTO> enderecos = enderecoService.listarTodos();

        return ResponseEntity.ok(enderecos);

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca endereços por CEP", description = "Retorna os endereços correspondentes ao CEP informado")
    @GetMapping("/buscar/cep/{cep}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCep(@PathVariable String cep) {

        try {

            List<EnderecoDTO> endereco = enderecoService.buscaPorCep(cep);

            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca endereços por logradouro", description = "Retorna os endereços correspondentes ao logradouro informado")
    @GetMapping("/buscar/logradouro/{logradouro}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorLogradouro(@PathVariable String logradouro) {

        try {

            List<EnderecoDTO> endereco = enderecoService.buscaPorLogradouro(logradouro);

            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca endereços por bairro", description = "Retorna os endereços correspondentes ao bairro informado")
    @GetMapping("/buscar/bairro/{bairro}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorBairro(@PathVariable String bairro) {

        try {

            List<EnderecoDTO> endereco = enderecoService.buscaPorBairro(bairro);

            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }

    @SuppressWarnings("null")
    @Operation(summary = "Busca endereços por cidade", description = "Retorna os endereços correspondentes à cidade informada")
    @GetMapping("/buscar/cidade/{cidade}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidade(@PathVariable String cidade) {

        try {

            List<EnderecoDTO> endereco = enderecoService.buscaPorCidade(cidade);

            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }

    @Operation(summary = "Cadastra um endereço", description = "Adiciona um novo endereço ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<EnderecoDTO>> cadastrarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {

        try {

            EnderecoDTO novoEndereco = enderecoService.salvar(enderecoDTO);

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(novoEndereco);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro ao cadastrar endereço", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Altera um endereço", description = "Altera um endereço no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnderecoDTO>> alterarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {

        try {

            EnderecoDTO novoEndereco = enderecoService.atualizar(id, enderecoDTO);

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(novoEndereco);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Endereço não encontrado", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Altera parcialmente um endereço", description = "Altera parcialmente um endereço no sistema")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EnderecoDTO>> alterarParcialmenteEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {

        try {

            EnderecoDTO novoEndereco = enderecoService.atualizarParcialmente(id, enderecoDTO);

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(novoEndereco);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            ErrorResponse errorResponse = new ErrorResponse("Endereço não encontrado", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Erro interno", e.getMessage());

            ApiResponse<EnderecoDTO> response = new ApiResponse<>(errorResponse);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    @Operation(summary = "Deleta um endereço", description = "Remove um endereço do sistema pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {

        enderecoService.deletar(id);

        return ResponseEntity.noContent().build();

    }

}