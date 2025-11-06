package io.github.aplaraujo.library_api_estudo.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
