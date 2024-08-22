package com.shair13.data_service.service;

import com.shair13.data_service.dao.MovieSearchDao;
import com.shair13.data_service.dao.SearchRequest;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.exception.MovieNotFoundException;
import com.shair13.data_service.mapper.MovieMapper;
import com.shair13.data_service.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private final String TITLE = "NEW HOPE";
    private final String DIRECTOR = "George Lucas";
    private final String DESCRIPTION = "Obi-Wan tells young Luke that his father was a powerful Jedi Knight who fought in the Clone Wars.";
    private final double RATE = 7.9;
    @Mock
    private MovieRepository mockMovieRepository;
    @Mock
    private MovieMapper mockMovieMapper;
    @Mock
    private MovieSearchDao mockMovieSearch;
    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldSave() {
        // given
        Movie movie = new Movie(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        WriteMovieDto writeMovieDto = new WriteMovieDto(TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovieDto = new ReadMovieDto(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.save(movie)).thenReturn(movie);
        when(mockMovieMapper.toDomain(writeMovieDto)).thenReturn(movie);
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovieDto);

        // when
        ReadMovieDto result = movieService.save(writeMovieDto);

        // then
        assertEquals(readMovieDto, result);
    }

    @Test
    void shouldGetAll() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        SearchRequest searchRequest = new SearchRequest(TITLE, DIRECTOR, DESCRIPTION, RATE);

        Movie movieOne = new Movie(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        Movie movieTwo = new Movie(-2L, TITLE + "2", DIRECTOR, DESCRIPTION, RATE);
        List<Movie> movies = List.of(movieOne, movieTwo);

        ReadMovieDto readMovieDtoOne = new ReadMovieDto(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovieDtoTwo = new ReadMovieDto(-2L, TITLE + "2", DIRECTOR, DESCRIPTION, RATE);
        List<ReadMovieDto> readMovieDtoList = List.of(readMovieDtoOne, readMovieDtoTwo);

        PagedMovie pagedMovie = new PagedMovie(readMovieDtoList, pageable);

        when(mockMovieSearch.searchByCriteria(searchRequest, pageable)).thenReturn(movies);
        when(mockMovieMapper.toPagedMovie(movies, pageable)).thenReturn(pagedMovie);
        // when

        PagedMovie result = movieService.search(searchRequest, pageable);

        // then
        assertEquals(movies.size(), result.movies().size());
        assertEquals(page, result.pageable().getPageNumber());
        assertEquals(size, result.pageable().getPageSize());
    }

    @Test
    void shouldGetById() {
        // given
        Long id = 1L;
        Movie movie = new Movie(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovieDto = new ReadMovieDto(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovieDto);

        // when
        ReadMovieDto result = movieService.getById(id);

        // then
        assertEquals(readMovieDto, result);
    }

    @Test
    void getById_shouldThrowMovieNotFoundException() {
        // given
        Long id = 1L;

        // when
        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class,
                () -> movieService.getById(id));

        // then
        assertTrue(thrown.getMessage().contains("Movie with id = " + id + " not found"));
    }

    @Test
    void shouldUpdate() {
        // given
        Long id = 1L;
        Movie movie = new Movie(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        WriteMovieDto writeMovieDto = new WriteMovieDto(TITLE, DIRECTOR, DESCRIPTION, RATE);
        ReadMovieDto readMovieDto = new ReadMovieDto(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(mockMovieMapper.toReadDto(movie)).thenReturn(readMovieDto);

        // when
        ReadMovieDto result = movieService.update(id, writeMovieDto);

        // then
        assertEquals(readMovieDto, result);
    }

    @Test
    void update_shouldThrowMovieNotFoundException() {
        // given
        Long id = 1L;
        WriteMovieDto writeMovieDto = new WriteMovieDto(TITLE, DIRECTOR, DESCRIPTION, RATE);

        // when
        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class,
                () -> movieService.update(id, writeMovieDto));

        // then
        assertTrue(thrown.getMessage().contains("Movie with id = " + id + " not found"));
    }

    @Test
    void delete_shouldThrowMovieNotFoundException() {
        // given
        Long id = 1L;

        // when
        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class,
                () -> movieService.delete(id));

        // then
        assertTrue(thrown.getMessage().contains("Movie with id = " + id + " not found"));
    }
}