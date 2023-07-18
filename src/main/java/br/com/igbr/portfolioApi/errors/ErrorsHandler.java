package br.com.igbr.portfolioApi.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handlerErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerErro400(MethodArgumentNotValidException e ){
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataValidation:: new).toList());
    }

    public record DataValidation(String name, String message){
        public DataValidation(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
