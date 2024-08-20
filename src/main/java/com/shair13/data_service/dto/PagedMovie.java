package com.shair13.data_service.dto;

import com.shair13.data_service.dao.PageDetails;

import java.util.List;

public record PagedMovie(
        List<ReadMovieDto> movies,
        PageDetails pageDetails
) {
}