package com.shair13.data_service.service;

import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return movieRepository.findAll(pageable);
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Movie update(Long id, Movie updatedMovie) {
        Movie movie = getById(id);
        movie.updateMovie(updatedMovie);
        return movieRepository.save(movie);
    }

    public void delete(Long id) {
        Movie movie = getById(id);
        movieRepository.delete(movie);
    }
}
