package com.shair13.data_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadMovieDto {
    private Long id;
    private String title;
    private String director;
    private String description;
    private double rate;
}
