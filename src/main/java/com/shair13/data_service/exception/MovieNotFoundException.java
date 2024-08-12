package com.shair13.data_service.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(Long id) {
        super("Movie with id = " + id + " not found");
    }
}
