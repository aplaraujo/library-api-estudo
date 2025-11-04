package io.github.aplaraujo.library_api_estudo.controllers.common;

import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroCampo;
import io.github.aplaraujo.library_api_estudo.controllers.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Captura de exceções e dar uma resposta de erro ou sucesso
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) // Pega o erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Mapeamento do retorno do método
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> lista = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", lista);
    }
}
