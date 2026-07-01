package com.JoaoLucas.Sistema.Barbearia.exception;

import com.JoaoLucas.Sistema.Barbearia.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflito(ConflitoException ex) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.CONFLICT.value(), "Conflito", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Argumento inválido", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.UNAUTHORIZED.value(), "Credenciais não autorizadas", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex) {
        log.error("Erro Inesperado ", ex);
        ErrorResponseDTO errorMessage = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do sistema", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
