package com.shair13.data_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedMovie {
    private List<ReadMovieDto> movies;
    private int pageNumber;
    private int pageSize;
}