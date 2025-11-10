package io.github.aplaraujo.library_api_estudo.controllers.common;

import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroCampo;
import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroResposta;
import io.github.aplaraujo.library_api_estudo.exceptions.CampoInvalidoException;
import io.github.aplaraujo.library_api_estudo.exceptions.OperacaoNaoPermitidaException;
import io.github.aplaraujo.library_api_estudo.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Captura de exceções e dar uma resposta de erro ou sucesso
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // Pega o erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Mapeamento do retorno do método
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Erro de validação: {}", e);
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> lista = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", lista);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidException(CampoInvalidoException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException e) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        log.error("Erro inesperado: {}", e);
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado. Entre em contato com a administração do sistema", List.of());
    }
}
