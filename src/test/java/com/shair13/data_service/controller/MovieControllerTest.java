package com.shair13.data_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shair13.data_service.dto.PagedMovie;
import com.shair13.data_service.entity.Movie;
import com.shair13.data_service.repository.MovieRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldAddMovie_rateEqualsMaxPossibleValue() throws Exception {
        // given
        String jsonMovie = """
                {
                	"title": "New Hope",
                    "director": "George Lucas",
                    "description": "Some description",
                    "rate": 10.0
                }
                """;

        // when + then
        mockMvc.perform(post("/movies").contentType("application/json").content(jsonMovie))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", Matchers.is("New Hope")))
                .andExpect(jsonPath("$.director", Matchers.is("George Lucas")))
                .andExpect(jsonPath("$.description", Matchers.is("Some description")))
                .andExpect(jsonPath("$.rate", Matchers.is(10.0)));
    }

    @Test
    @Transactional
    void shouldAddMovie_rateEqualsMinPossibleValue() throws Exception {
        // given
        String jsonMovie = """
                {
                	"title": "New Hope",
                    "director": "George Lucas",
                    "description": "Some description",
                    "rate": 1.0
                }
                """;

        // when + then
        mockMvc.perform(post("/movies").contentType("application/json").content(jsonMovie))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", Matchers.is("New Hope")))
                .andExpect(jsonPath("$.director", Matchers.is("George Lucas")))
                .andExpect(jsonPath("$.description", Matchers.is("Some description")))
                .andExpect(jsonPath("$.rate", Matchers.is(1.0)));
    }

    @Test
    @Transactional
    void shouldAddMovie_shouldResponseBadRequest() throws Exception {
        // given
        String jsonMovie = """
                {
                	"title": "",
                    "director": "",
                    "description": "Some description",
                    "rate": 11.0
                }
                """;

        // when + then
        mockMvc.perform(post("/movies").contentType("application/json").content(jsonMovie))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void getAllMovies() throws Exception {
        // given + when
        movieRepository.save(new Movie(-1L, "New Hope", "George Lucas", "Description 1", 10.0));
        movieRepository.save(new Movie(-1L, "Inception", "Christopher Nolan", "Description 2", 10.0));

        MvcResult mvcResult = mockMvc.perform(get("/movies"))
                .andExpect(status().is(200))
                .andReturn();
        PagedMovie result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PagedMovie.class);

        // then
        assertEquals(2, result.getMovies().size());
    }

    @Test
    @Transactional
    void getAllMovies_getOnePerPage() throws Exception {
        // given
        movieRepository.save(new Movie(-1L, "New Hope", "George Lucas", "Description 1", 10.0));
        movieRepository.save(new Movie(-1L, "Inception", "Christopher Nolan", "Description 2", 10.0));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/movies?size=1"))
                .andExpect(status().is(200))
                .andReturn();
        PagedMovie result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PagedMovie.class);

        // then
        assertEquals(1, result.getMovies().size());
    }

    @Test
    @Transactional
    void shouldGetMovieById() throws Exception {
        // given
        Movie movie = movieRepository.save(new Movie(-1L, "New Hope", "George Lucas", "Description 1", 10.0));

        // when + then
        mockMvc.perform(get("/movies/" + movie.getId()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", Matchers.is("New Hope")))
                .andExpect(jsonPath("$.director", Matchers.is("George Lucas")))
                .andExpect(jsonPath("$.description", Matchers.is("Description 1")))
                .andExpect(jsonPath("$.rate", Matchers.is(10.0)));
    }

    @Test
    @Transactional
    void shouldGetMovieById_shouldThrowMovieNotFoundException() throws Exception {
        // given
        Long id = 13L;

        // when + then
        mockMvc.perform(get("/movies/" + id))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.error", Matchers.containsString("Movie with id = " + id + " not found")));
    }

    @Test
    @Transactional
    void shouldSearchMovie() throws Exception {
        // given
        String title = "incep";
        String director = "nolan";
        double rateGreaterThan = 7.0;
        movieRepository.save(new Movie(-1L, "Inception", "Christopher Nolan", "Description 1", 10.0));
        movieRepository.save(new Movie(-1L, "Inception", "Władysław Jagiełlo", "Description 1", 10.0));
        movieRepository.save(new Movie(-1L, "Inception", "Christopher Nolan", "Description 1", 4.0));
        movieRepository.save(new Movie(-1L, "Król Lew", "Christopher Nolan", "Description 1", 10.0));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/movies/search?title=" + title + "&director=" + director + "&rate-gt=" + rateGreaterThan))
                .andExpect(status().is(200))
                .andReturn();
        PagedMovie result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PagedMovie.class);

        // then
        assertEquals(1, result.getMovies().size());
    }

    @Test
    @Transactional
    void searchMovie_shouldReturnBadRequestWhenRateIsInvalid() throws Exception {
        // given
        String invalidRate = "bb";

        // when + then
        mockMvc.perform(get("/movies/search?rate-gt=" + invalidRate))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void shouldUpdateMovie() throws Exception {
        // given
        String jsonMovie = """
                {
                	"title": "Return of The Jedi",
                    "director": "Richard Marquand",
                    "description": "Description 2",
                    "rate": 9.0
                }
                """;

        Movie movie = movieRepository.save(new Movie(1L, "New Hope", "George Lucas", "Description 1", 10.0));

        // when + then
        mockMvc.perform(put("/movies/" + movie.getId()).contentType("application/json").content(jsonMovie))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.title", Matchers.is("Return of The Jedi")))
                .andExpect(jsonPath("$.director", Matchers.is("Richard Marquand")))
                .andExpect(jsonPath("$.description", Matchers.is("Description 2")))
                .andExpect(jsonPath("$.rate", Matchers.is(9.0)));
    }

    @Test
    @Transactional
    void updateMovie_shouldThrowMovieNotFoundException() throws Exception {
        // given
        Long id = 13L;
        String jsonMovie = """
                {
                	"title": "Return of The Jedi",
                    "director": "Richard Marquand",
                    "description": "Description 2",
                    "rate": 9.0
                }
                """;

        // when + then
        mockMvc.perform(put("/movies/" + id).contentType("application/json").content(jsonMovie))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.error", Matchers.containsString("Movie with id = " + id + " not found")));
        ;
    }

    @Test
    @Transactional
    void shouldDeleteMovie() throws Exception {
        // given
        Long id = 1L;
        Movie movie = movieRepository.save(new Movie(id, "New Hope", "George Lucas", "Description 1", 10.0));

        // when + then
        mockMvc.perform(delete("/movies/" + movie.getId()))
                .andExpect(status().is(200));
    }
}