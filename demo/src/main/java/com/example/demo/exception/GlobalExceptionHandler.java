package com.example.demo.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

/**
 * Centraliza o tratamento de erros de todos os controllers,
 * evitando try/catch repetido e garantindo o status HTTP correto.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> tratarNaoEncontrado(RecursoNaoEncontradoException e)
    {

        return resposta(HttpStatus.NOT_FOUND, "Recurso não encontrado", e.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> tratarValidacao(MethodArgumentNotValidException e)
    {

        String mensagens = e.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return resposta(HttpStatus.BAD_REQUEST, "Dados inválidos", mensagens);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> tratarCorpoInvalido(HttpMessageNotReadableException e)
    {

        return resposta(HttpStatus.BAD_REQUEST, "Requisição inválida",
                "O corpo da requisição está mal formatado ou contém valores inválidos (verifique os enums e tipos dos campos)");

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> tratarTipoParametro(MethodArgumentTypeMismatchException e)
    {

        return resposta(HttpStatus.BAD_REQUEST, "Parâmetro inválido",
                "Valor inválido para o parâmetro '" + e.getName() + "': " + e.getValue());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> tratarArgumentoInvalido(IllegalArgumentException e)
    {

        return resposta(HttpStatus.BAD_REQUEST, "Requisição inválida", e.getMessage());

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> tratarIntegridade(DataIntegrityViolationException e)
    {

        return resposta(HttpStatus.CONFLICT, "Conflito de dados",
                "A operação viola uma restrição do banco de dados (ex.: o registro está sendo usado por outro cadastro)");

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> tratarErroGenerico(Exception e)
    {

        // Exceções do próprio Spring (404 de rota, 405 de método etc.) mantêm o status original
        if(e instanceof org.springframework.web.ErrorResponse erroSpring)
        {

            return resposta(erroSpring.getStatusCode(), "Erro na requisição", e.getMessage());

        }

        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", e.getMessage());

    }

    private ResponseEntity<ApiResponse<Void>> resposta(HttpStatusCode status, String erro, String mensagem)
    {

        return ResponseEntity.status(status).body(new ApiResponse<>(new ErrorResponse(erro, mensagem)));

    }

}
