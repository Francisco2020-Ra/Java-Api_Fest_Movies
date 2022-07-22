package com.informatorio.festmovies.controller;

import com.informatorio.festmovies.exception.CategoryExistException;
import com.informatorio.festmovies.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.UnexpectedTypeException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CategoryExistException.class})
    protected ResponseEntity<Object> handleCategoryExistException(Exception ex, WebRequest request) {
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

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            message.add(error.getField() + " " + error.getDefaultMessage());
        }

        ApiErrorDTO apiError = ApiErrorDTO
                .builder()
                .timestamp(timestamp)
                .status(HttpStatus.BAD_REQUEST)
                .listMessage(message)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        ApiErrorDTO errorDTO = ApiErrorDTO.builder()
                .timestamp(timestamp)
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @ExceptionHandler(value = {UnexpectedTypeException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(Exception ex, WebRequest request) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String message = "Fields cannot be blank";
        ApiErrorDTO apiError = ApiErrorDTO
                .builder()
                .timestamp(timestamp)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class ApiErrorDTO {
        private Timestamp timestamp;
        private HttpStatus status;
        private String message;
        private List<String> listMessage;
    }
}
