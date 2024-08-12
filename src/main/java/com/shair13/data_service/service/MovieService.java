package com.shair13.data_service.service;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.exception.MovieNotFoundException;
import com.shair13.data_service.mapper.MovieMapper;
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
    private final MovieMapper movieMapper;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public PagedMovie getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Movie> result = movieRepository.findAll(pageable);
        return movieMapper.pageToPagedMovie(result);
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public Movie update(Long id, Movie movie) {
        if (movieRepository.existsById(id)) {
            movie.setId(id);
            return movieRepository.save(movie);
        }
        throw new MovieNotFoundException(id);
    }

    public void delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new MovieNotFoundException(id);
        }
    }
}
