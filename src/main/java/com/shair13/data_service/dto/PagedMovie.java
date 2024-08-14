package com.shair13.data_service.dto;

import com.shair13.data_service.dao.PageDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedMovie {
    private List<ReadMovieDto> movies;
    private PageDetails pageDetails;
}