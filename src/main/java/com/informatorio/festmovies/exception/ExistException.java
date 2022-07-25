package com.informatorio.festmovies.exception;

import org.springframework.web.util.NestedServletException;

public class ExistException extends NestedServletException {
    public ExistException(String message){
        super(message);
    }
}
