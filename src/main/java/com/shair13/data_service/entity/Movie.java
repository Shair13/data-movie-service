package com.shair13.data_service.entity;

import com.shair13.data_service.dto.WriteMovieDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String director;
    private String description;
    @Min(1)
    @Max(10)
    private Double rate;

    public void update(WriteMovieDto writeMovieDto) {
        title = writeMovieDto.title();
        director = writeMovieDto.director();
        description = writeMovieDto.description();
        rate = writeMovieDto.rate();
    }
}
