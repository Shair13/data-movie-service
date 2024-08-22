package com.shair13.data_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public record PagedMovie(
        List<ReadMovieDto> movies,
        Pageable pageable
) {
}