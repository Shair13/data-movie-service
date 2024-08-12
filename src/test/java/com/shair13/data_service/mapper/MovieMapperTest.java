package com.shair13.data_service.mapper;

import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    @Test
    void pageToPagedMovie() {
        // given
        int pageNumber = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("id"));

        Movie movie = new Movie(1L, "title", "director", "description", 5.0);
        List<Movie> movies = List.of(movie);

        Page<Movie> moviePage = new PageImpl<>(movies, pageable, 2);

        // when
        PagedMovie pagedMovie = MovieMapper.INSTANCE.pageToPagedMovie(moviePage);

        // then
        assertEquals(movies.size(), pagedMovie.getMovies().size());
        assertEquals(pageNumber, pagedMovie.getPageNumber());
        assertEquals(size, pagedMovie.getPageSize());
    }
}