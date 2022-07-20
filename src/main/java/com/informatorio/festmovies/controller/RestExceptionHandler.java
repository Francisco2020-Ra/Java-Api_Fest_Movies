package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.exception.CategoryExistException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value ={CategoryExistException.class})
    protected ResponseEntity<Object> handleCategoryExistException(Exception ex, WebRequest request){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        ApiErrorDTO errorDTO = ApiErrorDTO.builder()
                .timestamp(timestamp)
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
    return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> message = new ArrayList<>();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            message.add(error.getField() + " " + error.getDefaultMessage());
        }

        ApiErrorDTO apiError =ApiErrorDTO
                .builder()
                .timestamp(timestamp)
                .status(HttpStatus.BAD_REQUEST)
                .listMessage(message)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ApiErrorDTO{
        private Timestamp timestamp;
        private HttpStatus status;
        private String message;
        private List<String> listMessage;
    }
}
