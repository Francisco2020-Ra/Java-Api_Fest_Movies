package com.informatorio.festmovies.exception;

import org.springframework.web.util.NestedServletException;

public class CategoryExistException extends NestedServletException {
    public CategoryExistException(String message){
        super(message);
    }
}
