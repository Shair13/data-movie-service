package com.shair13.data_service.dto;

import com.shair13.data_service.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class PagedMovie {
    private List<Movie> movies;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
