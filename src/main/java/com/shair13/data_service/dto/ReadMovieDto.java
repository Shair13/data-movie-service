package com.shair13.data_service.dto;

public record ReadMovieDto (
    Long id,
    String title,
    String director,
    String description,
    double rate){
}
