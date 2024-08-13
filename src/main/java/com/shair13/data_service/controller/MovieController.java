package com.shair13.data_service.controller;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
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
    public ReadMovieDto addMovie(@RequestBody @Valid WriteMovieDto writeMovieDto) {
        return movieService.save(writeMovieDto);
    }

    @GetMapping("/{id}")
    public ReadMovieDto getMovieById(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @GetMapping
    public PagedMovie searchMovie(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id", name = "sort-by") @Pattern(regexp = "id|title|director|rate") String sortBy,
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String director,
            @RequestParam(defaultValue = "0", name = "rate-gt") @Min(0) @Max(10) Double rateGreaterThan) {
        return movieService.search(page, size, sortBy, title, director, rateGreaterThan);
    }

    @PutMapping("/{id}")
    public ReadMovieDto updateMovie(@PathVariable Long id, @RequestBody @Valid WriteMovieDto writeMovieDto) {
        return movieService.update(id, writeMovieDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }
}
