package com.shair13.data_service.service;

import com.shair13.data_service.dao.MovieSearchDao;
import com.shair13.data_service.dao.PageDetails;
import com.shair13.data_service.dao.SearchRequest;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.exception.MovieNotFoundException;
import com.shair13.data_service.mapper.MovieMapper;
import com.shair13.data_service.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieSearchDao movieSearch;
    private final MovieMapper movieMapper;

    public ReadMovieDto save(WriteMovieDto writeMovieDto) {
        Movie movie = movieRepository.save(movieMapper.toDomain(writeMovieDto));
        return movieMapper.toReadDto(movie);
    }

    public ReadMovieDto getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        return movieMapper.toReadDto(movie);
    }

    public PagedMovie search(SearchRequest searchRequest, PageDetails pageDetails) {
        List<Movie> movies = movieSearch.searchByCriteria(searchRequest, pageDetails);
        return movieMapper.toPagedMovie(movies, pageDetails);
    }

    public ReadMovieDto update(Long id, WriteMovieDto writeMovieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movie.update(writeMovieDto);
        movieRepository.save(movie);
        return movieMapper.toReadDto(movie);
    }

    public void delete(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movieRepository.delete(movie);
    }
}
