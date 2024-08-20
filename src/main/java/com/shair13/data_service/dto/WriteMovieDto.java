package com.shair13.data_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record WriteMovieDto(
    @NotBlank
    String title,
    @NotBlank
    String director,
    String description,
    @Min(1)
    @Max(10)
    double rate){
}
