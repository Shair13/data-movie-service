package com.shair13.data_service.mapper;

import com.shair13.data_service.dao.PageDetails;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.dto.ReadMovieDto;
import com.shair13.data_service.dto.WriteMovieDto;
import com.shair13.data_service.entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieMapperTest {

    private final Long ID = 1L;
    private final String TITLE = "NEW HOPE";
    private final String DIRECTOR = "George Lucas";
    private final String DESCRIPTION = "Obi-Wan tells young Luke that his father was a powerful Jedi Knight who fought in the Clone Wars.";
    private final double RATE = 7.9;

    @Test
    void shouldMap_fromListOfMoviesAndPageDetailsToPagedMovie() {
        //given
        Movie movieOne = new Movie(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);
        Movie movieTwo = new Movie(2L, "title 2", "director 2", "description 2", 10.0);
        List<Movie> movies = List.of(movieOne, movieTwo);
        PageDetails pageDetails = new PageDetails(0, 10, "id");

        //when
        PagedMovie result = MovieMapper.INSTANCE.toPagedMovie(movies, pageDetails);

        //then
        assertEquals(movies.size(), result.getMovies().size());
        assertEquals(TITLE, result.getMovies().get(0).getTitle());
        assertEquals(DIRECTOR, result.getMovies().get(0).getDirector());
        assertEquals(DESCRIPTION, result.getMovies().get(0).getDescription());
        assertEquals(RATE, result.getMovies().get(0).getRate());
        assertEquals(pageDetails.getPage(), result.getPageDetails().getPage());
        assertEquals(pageDetails.getSize(),  result.getPageDetails().getSize());
        assertEquals(pageDetails.getSortBy(),  result.getPageDetails().getSortBy());
    }

    @Test
    void shouldMap_fromMovieToReadMovieDto() {
        //given
        Movie movie = new Movie(ID, TITLE, DIRECTOR, DESCRIPTION, RATE);

        //when
        ReadMovieDto result = MovieMapper.INSTANCE.toReadDto(movie);

        //then
        assertEquals(ID, result.getId());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DIRECTOR, result.getDirector());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(RATE, result.getRate());
    }

    @Test
    void shouldMap_fromWriteMovieDtoToDomain() {
        //given
        WriteMovieDto writeMovieDto = new WriteMovieDto("title 1", "director 1", "description 1", 10.0);

        //when
        Movie result = MovieMapper.INSTANCE.toDomain(writeMovieDto);

        //then
        assertEquals(writeMovieDto.getTitle(), result.getTitle());
        assertEquals(writeMovieDto.getDirector(), result.getDirector());
        assertEquals(writeMovieDto.getDescription(), result.getDescription());
        assertEquals(writeMovieDto.getRate(), result.getRate());
    }
}