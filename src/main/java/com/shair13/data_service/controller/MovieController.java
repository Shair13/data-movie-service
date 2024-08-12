package com.shair13.data_service.controller;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public Movie addMovie(@RequestBody @Valid Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping
    public PagedMovie getAllMovies(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") @Pattern(regexp = "id|title|director|rate") String sortBy) {
        return movieService.getAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        return movieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }
}
