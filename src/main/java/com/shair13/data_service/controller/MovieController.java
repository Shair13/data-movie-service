package com.shair13.data_service.controller;

import com.shair13.data_service.dao.SearchRequest;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    ReadMovieDto addMovie(@RequestBody @Valid WriteMovieDto writeMovieDto) {
        return movieService.save(writeMovieDto);
    }

    @GetMapping("/{id}")
    ReadMovieDto getMovieById(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @GetMapping
    PagedMovie searchMovie(
            Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, name = "rate-gt") @Min(0) @Max(10) Double rateGreaterThan) {
        SearchRequest searchRequest = new SearchRequest(title, director, description, rateGreaterThan);
        return movieService.search(searchRequest, pageable);
    }

    @PutMapping("/{id}")
    ReadMovieDto updateMovie(@PathVariable Long id, @RequestBody @Valid WriteMovieDto writeMovieDto) {
        return movieService.update(id, writeMovieDto);
    }

    @DeleteMapping("/{id}")
    void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }
}
