package com.example.CalculadoraMetrosCuadrados.controller.advice;

import com.example.CalculadoraMetrosCuadrados.dto.error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchFieldException.class})
    public ErrorDTO handleDistrictNotFoundException(NoSuchFieldException e) {
        return new ErrorDTO("Error en la busqueda del barrio", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> ListErrors = new ArrayList<>();
        for (ObjectError error1 : e.getAllErrors()) {
            ListErrors.add(error1.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorDTO("Informacion no valida", String.join(", ", ListErrors), LocalDateTime.now()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        ErrorDTO error = new ErrorDTO("Informacion del Request no valida", e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
