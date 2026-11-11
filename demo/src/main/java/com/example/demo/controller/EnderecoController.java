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

        return ResponseEntity.ok(enderecoService.listarTodos());

    }

    @Operation(summary = "Busca um endereço por ID", description = "Retorna os detalhes de um endereço")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(enderecoService.buscaPorId(id));

    }

    @Operation(summary = "Busca endereços por CEP", description = "Retorna os endereços correspondentes ao CEP informado")
    @GetMapping("/buscar/cep/{cep}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCep(@PathVariable String cep) {

        return ResponseEntity.ok(enderecoService.buscaPorCep(cep));

    }

    @Operation(summary = "Busca endereços por logradouro", description = "Retorna os endereços correspondentes ao logradouro informado")
    @GetMapping("/buscar/logradouro/{logradouro}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorLogradouro(@PathVariable String logradouro) {

        return ResponseEntity.ok(enderecoService.buscaPorLogradouro(logradouro));

    }

    @Operation(summary = "Busca endereços por bairro", description = "Retorna os endereços correspondentes ao bairro informado")
    @GetMapping("/buscar/bairro/{bairro}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorBairro(@PathVariable String bairro) {

        return ResponseEntity.ok(enderecoService.buscaPorBairro(bairro));

    }

    @Operation(summary = "Busca endereços por cidade", description = "Retorna os endereços correspondentes à cidade informada")
    @GetMapping("/buscar/cidade/{cidade}")
    public ResponseEntity<List<EnderecoDTO>> buscarPorCidade(@PathVariable String cidade) {

        return ResponseEntity.ok(enderecoService.buscaPorCidade(cidade));

    }

    @Operation(summary = "Cadastra um endereço", description = "Adiciona um novo endereço ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<EnderecoDTO>> cadastrarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {

        EnderecoDTO novoEndereco = enderecoService.salvar(enderecoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(novoEndereco));

    }

    @Operation(summary = "Altera um endereço", description = "Altera todos os dados de um endereço no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnderecoDTO>> alterarEndereco(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {

        EnderecoDTO enderecoAtualizado = enderecoService.atualizar(id, enderecoDTO);

        return ResponseEntity.ok(new ApiResponse<>(enderecoAtualizado));

    }

    @Operation(summary = "Altera parcialmente um endereço", description = "Altera apenas os campos enviados de um endereço")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EnderecoDTO>> alterarParcialmenteEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {

        EnderecoDTO enderecoAtualizado = enderecoService.atualizarParcialmente(id, enderecoDTO);

        return ResponseEntity.ok(new ApiResponse<>(enderecoAtualizado));

    }

    @Operation(summary = "Deleta um endereço", description = "Remove um endereço do sistema pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {

        enderecoService.deletar(id);

        return ResponseEntity.noContent().build();

    }

}
