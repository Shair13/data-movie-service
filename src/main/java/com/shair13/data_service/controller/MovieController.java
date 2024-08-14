package com.shair13.data_service.controller;

import com.shair13.data_service.dao.PageDetails;
import com.shair13.data_service.dao.SearchRequest;
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
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) Integer size,
            @RequestParam(required = false, name = "sort-by") @Pattern(regexp = "id|title|director|description|rate") String sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, name = "rate-gt") @Min(0) @Max(10) Double rateGreaterThan) {
        SearchRequest searchRequest = new SearchRequest(title, director, description, rateGreaterThan);
        PageDetails pageDetails = PageDetails.create(page, size, sortBy);
        return movieService.search(searchRequest, pageDetails);
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
