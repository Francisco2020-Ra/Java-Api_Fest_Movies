package com.informatorio.festmovies.exception;

import org.springframework.web.util.NestedServletException;

public class ResourceNotFoundException extends NestedServletException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}
