package com.shair13.data_service.service;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.exception.MovieNotFoundException;
import com.shair13.data_service.mapper.MovieMapper;
import com.shair13.data_service.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private final String TITLE = "NEW HOPE";
    private final String DIRECTOR = "George Lucas";
    private final String DESCRIPTION = "Obi-Wan tells young Luke that his father was a powerful Jedi Knight who fought in the Clone Wars.";
    private final double RATE = 7.9;
    @Mock
    private MovieRepository mockMovieRepository;
    @Mock
    private MovieMapper movieMapper;
    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldSave() {
        // given
        Movie movie = new Movie(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.save(movie)).thenReturn(movie);

        // when
        Movie result = movieService.save(movie);

        // then
        assertEquals(TITLE, result.getTitle());
        assertEquals(DIRECTOR, result.getDirector());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(RATE, result.getRate());
    }

    @Test
    void getAll() {
        // given
        int page = 0;
        int size = 10;
        String sortBy = "title";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Movie movieOne = new Movie(-1L, TITLE, DIRECTOR, DESCRIPTION, RATE);
        Movie movieTwo = new Movie(-2L, TITLE + "2", DIRECTOR, DESCRIPTION, RATE);
        List<Movie> movies = List.of(movieOne, movieTwo);

        Page<Movie> moviePage = new PageImpl<>(List.of(movieOne, movieTwo), pageable, 2);
        PagedMovie pagedMovie = new PagedMovie(movies, page, size, 2, 1, true);

        when(mockMovieRepository.findAll(pageable)).thenReturn(moviePage);
        when(movieMapper.pageToPagedMovie(moviePage)).thenReturn(pagedMovie);

        // when
        PagedMovie result = movieService.getAll(page, size, sortBy);

        // then
        assertEquals(movies.size(), result.getMovies().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
    }

    @Test
    void shouldGetById() {
        // given
        Long id = 1L;
        Movie movie = new Movie(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.findById(id)).thenReturn(Optional.of(movie));

        // when
        Movie result = movieService.getById(id);

        // then
        assertEquals(id, result.getId());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DIRECTOR, result.getDirector());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(RATE, result.getRate());
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
        when(mockMovieRepository.existsById(id)).thenReturn(true);
        when(mockMovieRepository.save(movie)).thenReturn(movie);

        // when
        Movie result = movieService.update(id, movie);

        // then
        assertEquals(id, result.getId());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DIRECTOR, result.getDirector());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(RATE, result.getRate());
    }
    @Test
    void update_shouldThrowMovieNotFoundException() {
        // given
        Long id = 1L;
        Movie movie = new Movie(id, TITLE, DIRECTOR, DESCRIPTION, RATE);
        when(mockMovieRepository.existsById(id)).thenReturn(false);

        // when
        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class,
                () -> movieService.update(id, movie));

        // then
        assertTrue(thrown.getMessage().contains("Movie with id = " + id + " not found"));
    }

    @Test
    void delete_shouldThrowMovieNotFoundException() {
        // given
        Long id = 1L;
        when(mockMovieRepository.existsById(id)).thenReturn(false);

        // when
        MovieNotFoundException thrown = assertThrows(MovieNotFoundException.class,
                () -> movieService.delete(id));

        // then
        assertTrue(thrown.getMessage().contains("Movie with id = " + id + " not found"));
    }
}